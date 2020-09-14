const getters = {
    access_token: state => state.user.access_token,
    refresh_token: state => state.user.refresh_token,
    roles: state => state.user.roles,
    userInfo: state => state.user.userInfo,
    message: state => state.message,
    selectedMenu:state => state.selectedMenu
    
}
export default getters
