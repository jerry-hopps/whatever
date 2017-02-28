import React from 'react'
import FilterLink from '../containers/FilterLink'

const Footer = ({ isFetching }) => (
    <ul className={"nav nav-tabs"} style={{margin:5}}>
      <li role="presentation" >
        <FilterLink filter="all">All</FilterLink>
      </li>
      <li role="presentation">
        <FilterLink filter="active">Active</FilterLink>
      </li>
      <li role="presentation">
        <FilterLink filter="completed">Completed</FilterLink>
      </li>
    </ul>
)

export default Footer