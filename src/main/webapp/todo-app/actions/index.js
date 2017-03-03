import queryString from 'query-string'
import uuid from 'uuid/v4'
import { showLoading, hideLoading } from 'react-redux-loading-bar'

export const setVisibilityFilter = (filter) => ({
  type: "SET_VISIBILITY_FILTER",
  filter
})

export const requestTodos = () => ({
  type: "REQUEST_TODOS"
})

export const receiveTodos = (json) => ({
  type: "RECEIVE_TODOS",
  todos: json.todos
})

export const fetchTodos = () => {

  return function(dispatch){
    dispatch(showLoading())
    dispatch(requestTodos())

    return fetch('/api/todos.json')
        .then(response => response.json())
        .then(json => {
          dispatch(receiveTodos(json))
          dispatch(hideLoading())
        })
  }
}

export const addTodo = (text) => {

  return function(dispatch){
    dispatch(showLoading())
    dispatch(requestTodos())

    const method = "POST"
    const body = queryString.stringify({ text: text, id: uuid() })
    const headers = {'Content-Type':'application/x-www-form-urlencoded'}

    return fetch("/api/todos.json", {method, headers, body } )
        .then(response => response.json())
        .then(json => {
          dispatch(receiveTodos(json))
          dispatch(hideLoading())
        })
  }
}

export const toggleTodo = (id) => {

  return function(dispatch) {
    dispatch(showLoading())
    dispatch(requestTodos())

    const method = "PUT"
    const headers = {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS'
    }

    return fetch("/api/todos.json?"+queryString.stringify({id: id}), {method, headers})
        .then(response => response.json())
        .then(json => {
          dispatch(receiveTodos(json))
          dispatch(hideLoading())
        })
  }
}

export const removeTodo = (id) => {

  return function(dispatch) {
    dispatch(showLoading())
    dispatch(requestTodos())

    var headers = {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS'
    }

    const config = {method: "DELETE", headers: headers}

    return fetch("/api/todos.json?"+queryString.stringify({id: id}), config)
        .then(response => response.json())
        .then(json => {
          dispatch(receiveTodos(json))
          dispatch(hideLoading())
        })
  }
}
