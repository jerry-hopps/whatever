import React from 'react'
import { Provider } from 'react-redux'
import { Router, Route, browserHistory } from 'react-router'
import App from './App'
import thunk from 'redux-thunk'
import reducers from '../reducers'
import createLogger from 'redux-logger'

import { createStore, applyMiddleware } from 'redux'

const middleware = [ thunk, createLogger() ]

const store = createStore(
    reducers,
    applyMiddleware(...middleware)
)

const Root = () => (
    <Provider store={store}>
      <Router history={browserHistory}>
        <Route path="/todos/(:filter)" component={App} />
      </Router>
    </Provider>
)

export default Root