import { connect } from 'react-redux'
import Footer from '../components/Footer'

const mapStateToProps = (state) => ({
  isFetching: state.isFetching
})

const ProgressFooter = connect(
  mapStateToProps
)(Footer)

export default ProgressFooter
