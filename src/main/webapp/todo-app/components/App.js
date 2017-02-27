import React, { Component } from 'react'
import { connect } from 'react-redux'
import VisibleTodoList from '../containers/VisibleTodoList'
import AddTodoBlock from '../containers/AddTodoBlock'
import ProgressFooter from '../containers/ProgressFooter'
import { fetchTodos } from '../actions'
import LoadingBar from 'react-redux-loading-bar'

class App extends Component {

  constructor({props, params}){
    super(props)
    this.params = params
    console.log(this.params)
  }

  componentDidMount(){
    const { dispatch } = this.props
    dispatch(fetchTodos())
  }

  componentWillReceiveProps({ params }){
    this.params = params
    console.log(this.params)
  }

  render() {
    return (
      <div style={{margin: 0}}>
        <LoadingBar style={{ backgroundColor: 'green' }}/>
        <ProgressFooter />
        <AddTodoBlock />
        <VisibleTodoList filter={this.params.filter || 'all'}/>
      </div>
    )
  }
}

export default connect()(App)
