export const tableOption = {
  'border': true,
  'index': true,
  'indexLabel': '序号',
  'stripe': true,
  'menuAlign': 'center',
  'menuWidth': 150,
  'align': 'center',
  'refreshBtn': true,
  'showClomnuBtn': false,
  'searchSize': 'mini',
  'addBtn': false,
  'editBtn': false,
  'delBtn': false,
  'dicUrl': '/admin/dict/type/log_type',
  props: {
    label: 'label',
    value: 'value'
  },
  'column': [{
    label: '类型',
    prop: 'type',
    type: 'select',
    dicData: 'log_type',
    search: true
  }, {
    label: '请求接口',
    prop: 'requestUri'
  }, {
    label: 'IP地址',
    prop: 'remoteAddr'
  }, {
    label: '请求方式',
    prop: 'method'
  }, {
    label: '客户端',
    prop: 'serviceId'
  }, {
    label: '请求时间',
    prop: 'time'
  }, {
    label: '创建时间',
    prop: 'createTime',
    type: 'datetime',
    format: 'yyyy-MM-dd HH:mm',
    valueForma: 'yyyy-MM-dd HH:mm:ss'
  }]
}
