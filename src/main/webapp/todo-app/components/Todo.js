import React from 'react'
import { ListGroupItem, Button, Glyphicon } from 'react-bootstrap'
import '../stylesheets/Todo.css'

const Todo = ({ onClick, onDblClick, completed, text }) => (
  <ListGroupItem>
    <span style={{fontSize : 8, color: completed ? "green" : "black"}}><Glyphicon glyph={completed ? "check" : "unchecked"} /></span> {" "}
    <span onClick={onClick} style={{ textDecoration: completed ? 'line-through' : 'none' }}>{text}</span>
    {"    "}
    <span>
      <Button className={"noborder pull-right"} bsSize="xsmall" onClick={
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