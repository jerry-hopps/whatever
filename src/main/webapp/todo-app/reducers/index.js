import { combineReducers } from 'redux'
import todos from './todos'
import visibilityFilter from './visibilityFilter'
import isFetching from './isFetching'
import { loadingBarReducer } from 'react-redux-loading-bar'

const todoApp = combineReducers({
  todos,
  visibilityFilter,
  isFetching,
  loadingBar: loadingBarReducer
})

export default todoApp