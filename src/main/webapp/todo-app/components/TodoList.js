import React from 'react'
import Todo from './Todo'
import { ListGroup } from 'react-bootstrap'

const TodoList = ({ todos, onTodoClick, onTodoDbClick }) => (
  <ListGroup>
    {todos.map(todo =>
      <Todo key={todo.id}
        {...todo}
        onClick={() => onTodoClick(todo.id)}
        onDblClick={() => onTodoDbClick(todo.id)}
      />
    )}
  </ListGroup>
)

export default TodoList