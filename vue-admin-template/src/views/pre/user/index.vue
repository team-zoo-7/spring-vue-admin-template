<template>
  <el-container>
    <el-header>
      <div v-title>사용자</div>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input v-model="page.keywords" placeholder="키워드를 입력하세요" />
        </el-col>
        <el-col :span="6">
          <el-button type="primary" icon="el-icon-search" @click="getUserTableData">검색</el-button>
        </el-col>
        <el-col :span="4">
          <el-button v-has="'pre_user:create'" type="info" @click="addUserEntity">사용자 추가</el-button>
        </el-col>
        <el-col :span="6">
          <!-- 수정 사용자 정보 -->
          <el-dialog :visible.sync="dialog.visible" :title="dialog.title" width="40%">
            <el-form id="user" ref="entity" :model="entity" :rules="rules" label-width="80px">
              <template v-if="entity.uid != ''">
                <el-form-item label="사용자ID">
                  <el-input v-model="entity.uid" disabled />
                </el-form-item>
              </template>
              <el-form-item label="아바타" prop="avatar">
                <user-avatar :avatar="entity.avatar" @getAvatar="getAvatar" />
              </el-form-item>
              <el-form-item label="별명" prop="nickname">
                <el-input v-model="entity.nickname" clearable />
              </el-form-item>
              <el-form-item label="사용자이름" prop="username">
                <el-input v-model="entity.username" clearable />
              </el-form-item>
              <template v-if="entity.uid == ''">
                <el-form-item label="암호" prop="password">
                  <el-input v-model="entity.password" type="password" clearable />
                </el-form-item>
              </template>
              <el-form-item label="이메일" prop="email">
                <el-input v-model="entity.email" clearable />
              </el-form-item>
              <el-form-item label="생일" prop="gender">
                <el-date-picker
                  v-model="entity.birthday"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="날짜 선택"
                />
              </el-form-item>
              <el-form-item label="성별" prop="birthday">
                <el-select v-model="entity.gender" placeholder="선택하세요">
                  <el-option
                    v-for="item in genderOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="부서" prop="dept">
                <el-select v-model="entity.deptId" placeholder="선택하세요">
                  <el-option
                    v-for="item in deptList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="상태">
                <el-select v-model="entity.status" placeholder="선택하세요">
                  <el-option
                    v-for="item in optionsStatus"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialog.visible = false">취 소</el-button>
              <el-button type="primary" @click="saveAndFlush">확 인</el-button>
            </span>
          </el-dialog>

          <!-- 사용자 역할 수정 -->
          <el-dialog :visible.sync="userRole.visible" :title="userRole.title" width="30%">
            <el-checkbox-group v-model="entity.roles">
              <el-checkbox
                v-for="(item,index) in roleList"
                :label="item.rid"
                :key="index"
              >{{ item.description }}</el-checkbox>
            </el-checkbox-group>
            <span slot="footer" class="dialog-footer">
              <el-button @click="userRole.visible = false">취 소</el-button>
              <el-button type="primary" @click="saveUserRoles">확 인</el-button>
            </span>
          </el-dialog>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <el-table v-loading="loading" :data="tableData" border style="width: 100%">
        <el-table-column prop="nickname" label="사용자별명" fixed width="100" />
        <el-table-column prop="uid" label="사용자ID" width="80" />
        <el-table-column label="아바타" width="80">
          <template slot-scope="scope">
            <div class="avatar-wrapper" @click="viewBigAvatar(scope.row.avatar)">
              <img :src="scope.row.avatar" class="user-avatar" >
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="사용자이름" />
        <el-table-column label="성별" width="50">
          <template slot-scope="scope">{{ scope.row.gender | showGender }}</template>
        </el-table-column>
        <el-table-column label="생일" width="100">
          <template slot-scope="scope">{{ scope.row.birthday | formatDate }}</template>
        </el-table-column>
        <el-table-column prop="email" label="이메일" />
        <el-table-column label="역할">
          <template slot-scope="scope">
            <el-tooltip
              v-for="(role , index) in scope.row.roles"
              :key="index"
              :content="role.description"
              class="item"
              effect="dark"
              placement="top-start"
            >
              <el-tag size="mini">{{ role.roleName }}</el-tag>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="부서" width="100">
          <template slot-scope="scope">
            <el-tag size="mini">{{ scope.row.departmentName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="상태" width="70">
          <template slot-scope="scope">{{ scope.row.status | showStatus }}</template>
        </el-table-column>
        <el-table-column label="등록일시" width="95">
          <template slot-scope="scope">{{ scope.row.createTime | formatDateTime }}</template>
        </el-table-column>
        <el-table-column label="수정일시" width="95">
          <template slot-scope="scope">{{ scope.row.lastUpdateTime | formatDateTime }}</template>
        </el-table-column>
        <el-table-column fixed="right" label="운영" width="260">
          <template slot-scope="scope">
            <el-button
              v-has="'pre_user:update:roles'"
              type="info"
              size="small"
              @click="updateUserRole(scope.row)"
            >역할</el-button>
            <el-button
              v-has="'pre_user:update'"
              type="primary"
              size="small"
              @click="updateUserEntity(scope.row)"
            >수정</el-button>
            <el-button
              v-has="'pre_user:delete'"
              type="danger"
              size="small"
              @click="deleteUserEntity(scope.row)"
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
  getUserPage,
  saveUser,
  updateUser,
  removeUserById,
  updateUserRoles
} from '@/api/sysUser'
import { getDeptAll } from '@/api/sysDept'
import { getRoleAll } from '@/api/sysRole'

import UserAvatar from '@/components/UserAvatar'

export default {
  name: 'User',
  components: {
    UserAvatar
  },
  data() {
    return {
      page: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
        total: 0
      },
      entity: {
        uid: '',
        avatar: '', // 아바타
        username: '', // 로그인 계정
        email: '', // 이메일
        nickname: '', // 별명
        password: '', // 암호
        gender: '', // 성별
        birthday: '', // 생일
        status: 0, // 상태
        deptId: 0, // 부서ID
        roles: [] // 역할
      },
      dialog: {
        visible: false,
        title: ''
      },
      userRole: {
        visible: false,
        title: ''
      },
      loading: false,
      tableData: [],
      deptList: [],
      roleList: [],
      optionsStatus: [
        { value: 0, label: '비활성화' },
        { value: 1, label: '정상' },
        { value: 2, label: '숨김' }
      ],
      genderOptions: [
        { value: 0, label: '녀' },
        { value: 1, label: '남' },
        { value: 2, label: '미상' }
      ],
      // 유효성 검사 규칙
      rules: {
        avatar: [
          { required: true, message: '아바타은 비워 둘 수 없습니다.', trigger: 'blur' },
          {
            validator: function(rule, value, callback) {
              const regex = /^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/
              if (!regex.test(value)) {
                callback(new Error('아바타 주소 형식이 규정되지 않았습니다.'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        username: [
          { required: true, message: '로그인 사용자이름는 비워 둘 수 없습니다.', trigger: 'blur' },
          {
            validator: function(rule, value, callback) {
              const regex = /^[a-zA-Z0-9_-]{6,16}$/
              if (!regex.test(value)) {
                callback(
                  new Error(
                    '사용자이름으로 시작，길이는 6 ~ 16이며 문자, 숫자, 밑줄 만 포함 할 수 있습니다.'
                  )
                )
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        nickname: [
          { required: true, message: '별명을 입력해야합니다.', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '암호는 비워 둘 수 없습니다', trigger: 'blur' },
          {
            validator: function(rule, value, callback) {
              const regex = /^[a-zA-Z]\w{5,17}$/
              if (!regex.test(value)) {
                callback(
                  new Error(
                    '암호는 문자로 시작합니다，길이는 6 ~ 18이며 ​​문자, 숫자, 밑줄 만 포함 할 수 있습니다.'
                  )
                )
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        email: [
          { required: true, message: '이메일은 비워 둘 수 없습니다.', trigger: 'blur' },
          {
            validator: function(rule, value, callback) {
              const regex = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
              if (!regex.test(value)) {
                callback(new Error('이메일 주소 형식이 잘못되었습니다.'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ]
      }
    }
  },
  created() {
    this.getUserTableData()
    this.getDeptListData()
  },
  methods: {
    getUserTableData() {
      const _this = this
      _this.loading = true
      getUserPage(_this.page)
        .then(result => {
          _this.tableData = result.list
          _this.page.total = result.total
          _this.loading = false
        })
    },
    getDeptListData() {
      // 부서 컬럼 테이블 가져 오기
      const _this = this
      _this.loading = true
      getDeptAll()
        .then(result => {
          _this.deptList = result
          _this.loading = false
        })
    },
    getRoleListData() {
      // 역할 목록 테이블 가져 오기
      const _this = this
      _this.loading = true
      getRoleAll()
        .then(result => {
          _this.roleList = result
          _this.loading = false
        })
    },
    currentChange(index) {
      // 페이지수 변경
      this.page.pageNum = index
      this.getUserTableData()
    },
    viewBigAvatar(url) {
      // 큰 아바타보기
      window.open(url)
    },
    updateUserRole(data) {
      // 사용자 권한 재 할당
      this.getRoleListData()
      this.emptyEntity()
      this.userRole = {
        visible: true,
        title: '사용자 역할 수정'
      }
      const roles = []
      data.roles.forEach(role => {
        roles.push(role.rid)
      })
      this.entity.uid = data.uid
      this.entity.nickname = data.nickname
      this.entity.roles = roles
    },
    saveUserRoles() {
      // 사용자의 역할 정보 저장
      const _this = this
      const roles = this.entity.roles
      if (roles !== null && roles.length > 0) {
        _this
          .$confirm(
            '결정에서 수정으로【' + _this.entity.nickname + '】역할? 계속 하시겠습니까??',
            '경고',
            {
              confirmButtonText: '결정',
              cancelButtonText: '취소',
              type: 'warning'
            }
          )
          .then(() => {
            updateUserRoles({ uid: _this.entity.uid, roles: roles })
              .then(result => {
                _this.$message({ type: 'success', message: '수정역할성공!' })
                _this.userRole.visible = false
                _this.getUserTableData()
              })
          })
      } else {
        _this.$notify.error({
          title: '오류',
          message: '수정하기 전에 역할을 선택하세요.'
        })
      }
    },
    getAvatar(data) {
      // 업로드 된 아바타의 가치 얻기
      this.entity.avatar = data
    },
    emptyEntity() {
      // 사용자 정보 지우기
      this.entity = {
        uid: '',
        avatar: '', // 아바타
        username: '', // 로그인 계정
        email: '', // 이메일
        nickname: '', // 별명
        password: '', // 암호
        gender: '', // 성별
        birthday: '', // 생일
        status: 1, // 상태
        deptId: 0, // 부서ID
        roles: [] // 역할
      }
    },
    addUserEntity() {
      // 추가사용자
      this.getDeptListData()
      this.emptyEntity()
      this.dialog = {
        visible: true,
        title: '新建사용자'
      }
    },
    updateUserEntity(data) {
      // 수정사용자정보
      this.emptyEntity()
      this.entity = {
        uid: data.uid,
        avatar: data.avatar, // 아바타
        username: data.username, // 로그인 계정
        email: data.email, // 이메일
        nickname: data.nickname, // 별명
        password: data.password, // 암호
        gender: data.gender, // 성별
        birthday: data.birthday, // 생일
        status: data.status, // 상태
        deptId: data.deptId, // 부서ID
        roles: data.roles // 역할
      }
      this.dialog = {
        visible: true,
        title: '수정사용자정보'
      }
    },
    deleteUserEntity(data) {
      // 이 사용자 삭제
      const _this = this
      if (data.uid !== null && data.uid !== '') {
        _this
          .$confirm(
            '결정후 삭제【' + data.nickname + '】계속 하시겠습니까?',
            '경고',
            {
              confirmButtonText: '결정',
              cancelButtonText: '취소',
              type: 'warning'
            }
          )
          .then(() => {
            removeUserById(data.uid)
              .then(result => {
                _this.$message({ type: 'success', message: '삭제성공!' })
                _this.getUserTableData()
              })
          })
      } else {
        _this.$notify.error({
          title: '오류',
          message: '삭제하기 전에 사용자를 선택하십시오'
        })
      }
    },
    saveAndFlush() {
      const _this = this
      _this.$refs.entity.validate(valid => {
        if (valid) {
          delete _this.entity.roles
          if (_this.entity.uid !== '') {
            updateUser(_this.entity)
              .then(result => {
                _this.$notify({
                  title: '성공',
                  message: '수정사용자성공!',
                  type: 'success'
                })
                _this.getUserTableData()
                _this.dialog.visible = false
              })
          } else {
            saveUser(_this.entity)
              .then(result => {
                _this.$notify({
                  title: '성공',
                  message: '추가사용자성공!',
                  type: 'success'
                })
                _this.getUserTableData()
                _this.dialog.visible = false
              })
          }
        }
      })
    }
  }
}
</script>
<style lang='scss' scoped>
.avatar-wrapper {
  cursor: pointer;
  position: relative;
  .user-avatar {
    width: 50px;
    height: 50px;
    border-radius: 10px;
  }
}
</style>
<style>
.el-tag:hover {
  cursor: pointer;
}
</style>
