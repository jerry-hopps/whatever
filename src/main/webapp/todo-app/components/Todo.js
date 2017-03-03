import React from 'react'
import { ListGroupItem, Button, Glyphicon } from 'react-bootstrap'
import '../stylesheets/Todo.css'

const Todo = ({ onClick, onDblClick, completed, text }) => (
  <ListGroupItem>
    <span onClick={onClick} style={{fontSize : 14, color: completed ? "green" : "green", cursor: 'pointer'}}><Glyphicon glyph={completed ? "check" : "unchecked"} /></span> {" "}
    <span style={{ textDecoration: completed ? 'line-through' : 'none' }}>{text}</span>
    {"    "}
    <span>
      <Button className={ "noborder pull-right"} bsSize="xsmall" onClick={
        (e) => {e.preventDefault(); onDblClick();}
      }>
        <Glyphicon glyph="trash" />
      </Button>
      {/*<Button className="noborder pull-right" bsSize="xsmall">*/}
        {/*<Glyphicon glyph="edit" />*/}
      {/*</Button>*/}
    </span>
  </ListGroupItem>
)

export default Todo