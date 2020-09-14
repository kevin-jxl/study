
import axios from './http.js'


// export const getUserMenu = (platformName) => { return axios.get(`/admin/menu/userMenu/${platformName}`).then(res => res) } //菜单 platformName
export const getUserMenu = (platformName) => { return axios.get(`/admin/menu/userMenu/`,{params: platformName}).then(res => res) } //菜单 platformName

//登录
export const getUserInfo = () => { return axios.get(`/admin/user/info`, {}).then(res => res) }            //例：post 请求
export const logout = () => { return axios.get(`/auth/oauth/removeToken`, {}).then(res => res) } // 退出登录请求

export const fetchList = (params) => { return axios.get(`/admin/log/logPage`, params).then(res => res) }            //例：post 请求
export const getDownloadExcel = (params) => { return axios.get(`/admin/log/downloadExcel`, params).then(res => res) } // 退出登录请求

// 考勤管理   page limit
export const getCompanyworkList = (params) => { return axios.get(`/spark/companywork/page`, {params: params}).then(res => res) }  //list
export const getCompanyworkUpdate = (params) => { return axios.put(`/spark/companywork`, params).then(res => res) } //修
export const getCompanyworkAdd = (params) => { return axios.post(`/spark/companywork`, params).then(res => res) }  //增
export const getCompanyworkDelete = (id) => { return axios.delete(`/spark/companywork/${id}`).then(res => res) } //删除
export const getCompanyworkInfo = (id) => { return axios.get(`/spark/companywork/${id}`).then(res => res) } //查询详情

// 值班管理   page limit
export const getCompanydutyList = (params) => { return axios.post(`/spark/companyduty/page`,  params).then(res => res) }  //list
export const getCompanydutyUpdate = (params) => { return axios.put(`/spark/companyduty`, params).then(res => res) } //修
export const getCompanydutyAdd = (params) => { return axios.post(`/spark/companyduty`, params).then(res => res) }  //增
export const getCompanydutyDelete = (id) => { return axios.delete(`/spark/companyduty/${id}`).then(res => res) } //删除
export const getCompanydutyInfo = (id) => { return axios.get(`/spark/companyduty/${id}`).then(res => res) } //查询详情
export const getSysadministrativeorganInputList = (params) => { return axios.get(`/admin/sysadministrativeorgan/inputList`,{params: params}).then(res => res) } //获取车场下拉列表
export const getsyscompany = (params) => { return axios.get(`/admin/syscompany`,params).then(res => res) } //获取企业下拉列表
export const getCompanypeopleInputList = (params) => { return axios.get(`/spark/companypeople/inputList`,{params: params}).then(res => res) } //获取人员下拉列表
export const getCompanydutyDutyPeople = (params) => { return axios.post(`/spark/companyduty/dutyPeople/list`, params).then(res => res) }  //值班详情列表

// 人员管理   page limit
export const getCompanypeopleList = (params) => { return axios.get(`/spark/companypeople/page`, {params: params}).then(res => res) }  //list
export const getCompanypeopleUpdate = (params) => { return axios.put(`/spark/companypeople`, params).then(res => res) } //修
export const getCompanypeopleAdd = (params) => { return axios.post(`/spark/companypeople`, params).then(res => res) }  //增
export const getCompanypeopleDelete = (id) => { return axios.delete(`/spark/companypeople/${id}`).then(res => res) } //删除
export const getCompanypeopleInfo = (id) => { return axios.get(`/spark/companypeople/${id}`).then(res => res) } //查询详情

// 文档管理   page limit
export const getCompanyfileList = (params) => { return axios.get(`/spark/companyfile/page`, {params: params}).then(res => res) }  //list
export const getCompanyfileUpdate = (params) => { return axios.put(`/spark/companyfile`, params).then(res => res) } //修
export const getCompanyfileAdd = (params) => { return axios.post(`/spark/companyfile`, params).then(res => res) }  //增
export const getCompanyfileDelete = (id) => { return axios.delete(`/spark/companyfile/${id}`).then(res => res) } //删除
export const getCompanyfileInfo = (id) => { return axios.get(`/spark/companyfile/${id}`).then(res => res) } //查询详情

//调度管理 page limit
export const getWorkOrderDetails = (id) => { return axios.get(`/spark/schedule/workOrder/${id}`).then(res => res) } //工单详情
export const getWorkOrderDelete = (id) => { return axios.delete(`/spark/schedule/workOrder/${id}`).then(res => res) } //工单删除
export const putWorkOrderAssign = (params) => { return axios.put(`/spark/schedule/workOrder/assign`, params).then(res => res) } //工单派遣
export const putWorkOrderSupervise = (params) => { return axios.put(`/spark/schedule/workOrder/supervise`, params).then(res => res) } //工单督办
export const putWorkOrderVerify = (params) => { return axios.put(`/spark/schedule/workOrder/verify`, params).then(res => res) } //工单核查
export const getWorkOrderAttachment = (groupId) => { return axios.get(`/spark/schedule/workOrder/attachment/${groupId}`).then(res => res) } //工单附件
export const getWorkOrderProcess = (workOrderId) => { return axios.get(`/spark/schedule/workOrder/process/${workOrderId}`).then(res => res) } //办理过程
export const getWorkOrderVerify = (workOrderId) => { return axios.get(`/spark/schedule/workOrder/verify/${workOrderId}`).then(res => res) } //工单核查信息
export const getWorkOrderList = (params) => { return axios.get(`/spark/schedule/workOrder/page/?page=${params.page}&size=${params.limit}&searchKey=${params.searchKey}&type=${params.type}&park=${params.park}`).then(res => res) } //工单管理列表
export const getWorkOrderAssignList = (params) => {return axios.get(`/spark/schedule/workOrderAssignList?page=${params.page}&size=${params.limit}&searchKey=${params.searchKey}&date=${params.date}&type=${params.type}`).then(res => res)} //业务调度列表
export const getWorkOrderStatistics = () => { return axios.get(`/spark/schedule/workOrderStatistics`, {}).then(res => res) }  //业务调度统计


//字典查询
export const getSyscompany = (tenantId) => {return axios.get(`/admin/syscompany?tenantId=${tenantId}`)} // 公司下拉数据查询
export const getPark = (comId) => {return axios.get(`/admin/sysadministrativeorgan/inputList?comId=${comId}`)} // 车场下拉数据查询
export const getOrgan = (organId) => {return axios.get(`/admin/sysserviceorgan/inputList?organId=${organId}`)} // 业务机构下拉数据查询
export const getPersonnel = (params) => {return axios.get(`/spark/companypeople/page?page=1&limit=1000&name=${params.name}&comId=${params.comId}&parkingLot=${params.parkId}&company=${params.serviceId}`).then(res => res)} // 人员数据查询




