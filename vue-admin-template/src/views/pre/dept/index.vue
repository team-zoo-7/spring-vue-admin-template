<!-- 부서 -->
<template>
  <el-container id="dept">
    <el-header>
      <div v-title>부서</div>
      <el-alert :title="website.dept.alert" type="info" />
    </el-header>
    <el-main v-loading="loading">
      <el-row :gutter="20">
        <el-col :span="10">
          <div class="grid-content bg-purple">
            <el-tree
              :data="treeData"
              :props="defaultProps"
              :default-expand-all="true"
              @node-click="handleNodeClick"
            />
          </div>
        </el-col>
        <el-col :span="14">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span class="card-header">부서 세부 정보</span>
              <el-button-group style="float: right;">
                <el-button
                  v-has="'pre_dept:create'"
                  type="primary"
                  size="mini"
                  icon="el-icon-share"
                  @click="addEntity"
                >부서 추가</el-button>
                <el-button
                  v-has="'pre_dept:update'"
                  type="warning"
                  size="mini"
                  icon="el-icon-edit"
                  @click=" isEdit = !isEdit "
                >부서 수정</el-button>
                <el-button
                  v-has="'pre_dept:delete'"
                  type="danger"
                  size="mini"
                  icon="el-icon-delete"
                  @click="deleteEntity"
                >부서 삭제</el-button>
              </el-button-group>
            </div>

            <el-form ref="deptForm" :rules="rules" :model="deptForm" label-width="100px">
              <el-form-item label="부서ID">
                <el-input v-model="deptForm.id" :disabled="true" />
              </el-form-item>
              <el-form-item label="부서이름" prop="name">
                <el-input :disabled="isEdit" v-model="deptForm.name" clearable />
              </el-form-item>
              <el-form-item label="부모부서" prop="parentId">
                <el-cascader
                  :disabled="isEdit"
                  :show-all-levels="false"
                  v-model="deptForm.parentId"
                  :options="treeData"
                  :props="cascaderProps"
                />
              </el-form-item>
              <el-form-item label="부서정렬" prop="level">
                <el-input-number
                  :disabled="isEdit"
                  :min="1"
                  :max="10000"
                  v-model="deptForm.level"
                  label="부서정렬"
                />
              </el-form-item>
              <el-form-item label="부서기술" prop="description">
                <el-input
                  :disabled="isEdit"
                  :autosize="{ minRows: 3, maxRows: 10}"
                  v-model="deptForm.description"
                  type="textarea"
                  clearable
                />
              </el-form-item>
              <el-form-item v-if="isEdit === false">
                <el-button type="primary" @click="saveAndFlush">저장</el-button>
                <el-button @click=" isEdit = !isEdit ">취소</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import {
  getDeptTree,
  saveDept,
  updateDept,
  removeDeptById
} from '@/api/sysDept'
import { mapGetters } from 'vuex'

export default {
  components: {},
  data() {
    return {
      deptForm: {
        id: 0,
        name: '',
        parentId: '',
        level: '',
        description: ''
      },
      loading: false,
      isEdit: true, // 편집 가능
      treeData: [],
      defaultLevel: 1,
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      cascaderProps: {
        children: 'children',
        label: 'title',
        value: 'id',
        emitPath: false,
        checkStrictly: true
      },
      // 유효성 검사 규칙
      rules: {
        name: [
          { required: true, message: '부서 이름은 비워 둘 수 없습니다.', trigger: 'blur' }
        ],
        parentId: [
          { required: true, message: '부모부서는 비워 둘 수 없습니다.', trigger: 'blur' }
        ],
        level: [
          {
            required: true,
            message: '부서 정렬은 비워 둘 수 없으며 숫자 여야합니다.',
            trigger: 'blur'
          }
        ],
        description: [
          { required: true, message: '부서 기술은 비워 둘 수 없습니다.', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapGetters(['website'])
  },
  created() {
    this.getDeptTreeData()
  },
  methods: {
    getDeptTreeData() {
      const _this = this
      _this.loading = true
      getDeptTree()
        .then(result => {
          _this.treeData = result
          _this.loading = false
        })
    },
    handleNodeClick(data) {
      this.isEdit = true
      this.deptForm = data.source
      if (data.children !== undefined) {
        this.defaultLevel = data.children.length + 1
      } else {
        this.defaultLevel = 1
      }
    },
    handleChange(data) {
      console.log('data :', data)
    },
    saveAndFlush() {
      const _this = this
      _this.$refs.deptForm.validate(valid => {
        if (valid) {
          // 수정부서 정보
          if (_this.deptForm.id > 0) {
            updateDept(_this.deptForm)
              .then(result => {
                _this.$notify({
                  title: '성공',
                  message: '수정부서 성공!',
                  type: 'success'
                })
                _this.getDeptTreeData()
                _this.isEdit = true
              })
          } else {
            // 추가부서
            saveDept(_this.deptForm)
              .then(result => {
                _this.$notify({
                  title: '성공',
                  message: '추가부서 성공!',
                  type: 'success'
                })
                _this.getDeptTreeData()
                _this.isEdit = true
              })
          }
        }
      })
    },
    addEntity() {
      this.isEdit = false
      const parentId = this.deptForm.id
      this.deptForm = {
        id: 0,
        name: '',
        parentId: parentId,
        level: this.defaultLevel,
        description: ''
      }
    },
    deleteEntity() {
      this.isEdit = true
      const _this = this
      if (_this.deptForm.id > 0) {
        _this
          .$confirm(
            '삭제 확인【' +
              _this.deptForm.name +
              '】?이부서에 사용자가 없는지 확인하십시오 그렇지 않으면 삭제가 불가능합니다. 계속 하시겠습니까?',
            '경고',
            {
              confirmButtonText: '결정',
              cancelButtonText: '취소',
              type: 'warning'
            }
          )
          .then(() => {
            removeDeptById(_this.deptForm.id)
              .then(result => {
                _this.$notify({
                  type: 'success',
                  title: '성공',
                  message: '삭제부서 성공!'
                })
                _this.getDeptTreeData()
              })
          })
      } else {
        _this.$notify.error({
          title: '오류',
          message: '삭제하기 전에 부서를 선택하세요.'
        })
      }
    }
  }
}
</script>
<style rel="stylesheet/scss" lang='scss' scoped>
.card-header {
  font-size: 1.5rem;
  font-weight: 600;
}
</style>

<style rel="stylesheet/scss" lang="scss">
#dept {
  .el-tree-node__expand-icon {
    font-size: 1rem;
  }
  .el-tree-node__label {
    font-size: 1rem;
  }
  .el-tree-node__content {
    height: 2.0rem;
  }
  .el-alert__title {
    font-size: 1rem;
  }
  .el-input-number {
    width: 100%;
  }
  .el-select {
    width: 100%;
  }
}
</style>
