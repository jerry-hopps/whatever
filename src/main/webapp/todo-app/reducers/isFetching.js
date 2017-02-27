const isFetching = (state = false, action) => {

  switch (action.type) {
    case "REQUEST_TODOS":
      return true
    case "RECEIVE_TODOS":
      return false
    default:
      return state
  }
}

export default isFetching
