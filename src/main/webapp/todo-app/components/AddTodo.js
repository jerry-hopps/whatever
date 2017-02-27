import React from 'react'
import {findDOMNode} from 'react-dom'
import { addTodo } from '../actions'
import { Glyphicon, FormGroup, InputGroup, FormControl } from 'react-bootstrap'

const AddTodo = ( { dispatch } ) => {

  let input

  const handleClick = e => {
    e.preventDefault()
    const inputCtl = findDOMNode(input)
    if (!inputCtl.value.trim()) {
      return
    }
    dispatch(addTodo(inputCtl.value))
    inputCtl.value = ''
  }

  const handleKeyPress = e => {
    if(e.key==="Enter"){
      handleClick(e)
    }
  }

  return (
    <div style={{margin:5}}>
        <FormGroup>
          <InputGroup>
            <InputGroup.Addon><Glyphicon glyph="tasks" /></InputGroup.Addon>
            <FormControl type="text" ref={node => {
              input = node
            }} onKeyPress={handleKeyPress}/>
          </InputGroup>
        </FormGroup>
    </div>
  )
}

export default AddTodo