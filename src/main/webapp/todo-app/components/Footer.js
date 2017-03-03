import React from 'react'
import FilterLink from '../containers/FilterLink'

const Footer = ({ filter }) => (
    <ul className={"nav nav-tabs"} style={{margin:5}}>
      <li role="presentation" className={ filter=="all" ? "active" : "" }>
        <FilterLink filter="all">All</FilterLink>
      </li>
      <li role="presentation" className={ filter=="active" ? "active" : "" }>
        <FilterLink filter="active">Active</FilterLink>
      </li>
      <li role="presentation" className={ filter=="completed" ? "active" : "" }>
        <FilterLink filter="completed">Completed</FilterLink>
      </li>
    </ul>
)

export default Footer