<template>
  <el-container>
    <el-header>
      <div v-title>역할</div>

      <el-row :gutter="20">
        <el-col :span="4">
          <el-button v-has="'pre_role:create'" type="primary" @click="addRoleEntity">역할 추가</el-button>
        </el-col>
        <el-col :span="20">
          <el-alert :title="website.role.alert" type="info" />
        </el-col>
      </el-row>

      <el-dialog :visible.sync="dialog.visible" :title="dialog.title" width="550px">
        <el-form id="role" ref="entity" :model="entity" :rules="rules" label-width="80px">
          <template v-if="entity.rid > 0">
            <el-form-item label="역할ID">
              <el-input v-model="entity.rid" disabled />
            </el-form-item>
          </template>
          <el-form-item label="역할이름" prop="roleName">
            <el-input v-model="entity.roleName" clearable />
          </el-form-item>
          <el-form-item label="역할기술" prop="description">
            <el-input v-model="entity.description" clearable />
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="dialog.visible = false">취 소</el-button>
          <el-button type="primary" @click="saveAndFlush">확 인</el-button>
        </span>
      </el-dialog>

      <!-- 역할 권한 Column 테이블 -->
      <el-dialog :visible.sync="authority.visible" :title="authority.title" width="400px">
        <el-tree
          ref="treeList"
          :data="authority.list"
          :props="authority.props"
          :default-checked-keys="authority.checkedKeys"
          show-checkbox
          highlight-current
          node-key="id"/>

        <span slot="footer" class="dialog-footer">
          <el-button @click="authority.visible = false">취 소</el-button>
          <el-button type="primary" @click="updateRolePermissions">확 인</el-button>
        </span>
      </el-dialog>
    </el-header>

    <el-main>
      <el-table v-loading="loading" :data="tableData" border style="width: 100%">
        <el-table-column fixed prop="rid" label="역할ID" width="80" />
        <el-table-column prop="roleName" label="역할이름" />
        <el-table-column prop="description" label="역할기술" />
        <el-table-column label="등록일시" width="95">
          <template slot-scope="scope">{{ scope.row.createTime | formatDateTime }}</template>
        </el-table-column>
        <el-table-column label="수정일시" width="95">
          <template slot-scope="scope">{{ scope.row.lastUpdateTime | formatDateTime }}</template>
        </el-table-column>
        <el-table-column fixed="right" label="운영" width="250">
          <template slot-scope="scope">
            <el-button type="info" size="small" @click="getRolePermissions(scope.row)">권한</el-button>
            <el-button
              v-has="'pre_role:update'"
              type="primary"
              size="small"
              @click="updateRoleEntity(scope.row)"
            >수정</el-button>
            <el-button
              v-has="'pre_role:delete'"
              type="danger"
              size="small"
              @click="deleteRoleEntity(scope.row)"
            >삭제</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer>
      <el-pagination
        :page-size="page.pageSize"
        :total="page.total"
        layout="prev, pager, next, jumper"
        background
        @current-change="currentChange"
      />
    </el-footer>
  </el-container>
</template>

<script>
import {
  getRolePage,
  getRoleById,
  saveRole,
  updateRole,
  removeRoleById,
  updateRolePermission
} from '@/api/sysRole'

import { mapGetters } from 'vuex'

export default {
  name: 'Role',
  components: {},
  data() {
    return {
      page: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
        total: 0
      },
      entity: {
        rid: 0,
        description: '',
        roleName: ''
      },
      dialog: {
        visible: false,
        title: ''
      },
      tableData: [],
      loading: false,
      authority: {
        list: [],
        props: {
          label: 'title',
          children: 'children'
        },
        checkedKeys: [],
        rid: 0,
        visible: false,
        title: ''
      },
      options: [
        { value: 0, label: '삭제' },
        { value: 1, label: '정상' },
        { value: 2, label: '숨김' }
      ],
      // 유효성 검사 규칙
      rules: {
        roleName: [
          { required: true, message: '역할 이름은 비워 둘 수 없습니다.', trigger: 'blur' },
          {
            validator: function(rule, value, callback) {
              const regex = /[R][O][L][E][_][A-Z]{4,}/
              if (!regex.test(value)) {
                callback(
                  new Error('역할 이름은 모두 영어 대문자 여야하며 "ROLE_"로 시작해야합니다.')
                )
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        description: [
          { required: true, message: '역할 기술은 비워 둘 수 없습니다.', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapGetters(['website'])
  },
  created() {
    this.getTableData()
  },
  methods: {
    getTableData() {
      // 역할 목록 테이블 가져 오기
      const _this = this
      _this.loading = true
      getRolePage(_this.page).then(result => {
        _this.tableData = result.list
        _this.page.total = result.total
        _this.loading = false
      })
    },
    currentChange(index) {
      // 페이지수 변경
      this.page.pageNum = index
      this.getTableData()
    },
    emptyEntity() {
      this.entity = {
        rid: 0,
        description: '',
        roleName: ''
      }
    },
    addRoleEntity() {
      this.emptyEntity()
      this.dialog.title = '역할 추가'
      this.dialog.visible = true
    },
    updateRoleEntity(data) {
      this.emptyEntity()
      this.entity = {
        rid: data.rid,
        roleName: data.roleName,
        description: data.description
      }
      this.dialog.title = '수정역할'
      this.dialog.visible = true
    },
    deleteRoleEntity(data) {
      // 삭제역할，현재 역할이 다른 사용자 또는 권한과 연결되어있는 경우 삭제는
      const _this = this
      if (data.rid > 0) {
        _this
          .$confirm(
            '삭제 확인【' +
              data.description +
              '】이 역할에 사용자가 없는지 확인하십시오. 그렇지 않으면 삭제가 불가능합니다. 계속 하시겠습니까?',
            '경고',
            {
              confirmButtonText: '결정',
              cancelButtonText: '취소',
              type: 'warning'
            }
          )
          .then(() => {
            removeRoleById(data.rid).then(result => {
              _this.$notify({
                title: '성공',
                message: '삭제성공!',
                type: 'success'
              })
              _this.getTableData()
            })
          })
      } else {
        _this.$notify.error({
          title: '오류',
          message: '삭제하기 전에 역할을 선택하세요.'
        })
      }
    },
    saveAndFlush() {
      const _this = this
      _this.$refs.entity.validate(valid => {
        if (valid) {
          if (_this.entity.rid > 0) {
            // 수정역할정보
            updateRole(_this.entity).then(result => {
              _this.$notify({
                title: '성공',
                message: '수정역할성공!',
                type: 'success'
              })
              _this.getTableData()
              _this.dialog.visible = false
            })
          } else {
            // 역할 추가
            saveRole(_this.entity).then(result => {
              _this.$notify({
                title: '성공',
                message: '역할 추가성공!',
                type: 'success'
              })
              _this.getTableData()
              _this.dialog.visible = false
            })
          }
        }
      })
    },
    getRolePermissions(data) {
      // 역할이 소유 한 현재 권한보기
      const _this = this
      if (data.rid > 0) {
        getRoleById(data.rid).then(result => {
          _this.authority.list = result.all
          _this.authority.checkedKeys = result.have
          _this.authority.rid = data.rid
          _this.authority.title = data.description
          _this.authority.visible = true
        })
      }
    },
    updateRolePermissions() {
      // 역할 수정 권한에게
      const _this = this
      let list = _this.$refs.treeList.getCheckedKeys()
      let father = _this.$refs.treeList.getHalfCheckedNodes()
      if (father !== undefined && father !== null && father.length > 0) {
        father.forEach(f => list.push(f.id))
      }
      updateRolePermission({
        rid: _this.authority.rid,
        permissions: list
      }).then(result => {
        _this.$notify({
          title: '성공',
          message: '수정역할권한성공!',
          type: 'success'
        })
        _this.getTableData()
        _this.authority.visible = false
      })
    }
  }
}
</script>
<style lang='scss' scoped>
</style>

<style>
#role .el-alert__title {
  font-size: 1rem;
}
</style>

