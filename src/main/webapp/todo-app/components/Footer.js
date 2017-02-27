import React from 'react'
import FilterLink from '../containers/FilterLink'

const Footer = ({ isFetching }) => (
  <div style={{margin:5}}>
    <FilterLink filter="all">All</FilterLink>
    {" / "}
    <FilterLink filter="active">Active</FilterLink>
    {" / "}
    <FilterLink filter="completed">Completed</FilterLink>
    <span style={{ display : "none" }}>fetching....</span>
  </div>
)

export default Footer