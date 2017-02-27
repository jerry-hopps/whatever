const todo = (state = {}, action) => {
  switch (action.type) {
    case 'ADD_TODO':
      return {
        id: action.id,
        text: action.text,
        completed: false
      }
    case 'TOGGLE_TODO':
      if (state.id !== action.id) {
        return state
      }

      return {
        ...state,
        completed: !state.completed
      }
    case 'REMOVE_TODO':
      return state.filter((todo) => action.id !== todo.id)
    default:
      return state
  }
}

const todos = (state = [], action) => {
  switch (action.type) {
    case 'RECEIVE_TODOS':
      return action.todos
    case 'ADD_TODO':
      return [
        ...state,
        todo(undefined, action)
      ]
    case 'TOGGLE_TODO':
      return state.map(t =>
        todo(t, action)
      )
    case 'REMOVE_TODO':
      return todo(state, action)
    default:
      return state
  }
}

export default todos
