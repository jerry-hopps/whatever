import { connect } from 'react-redux'
import Footer from '../components/Footer'

const mapStateToProps = (state) => ({
  activeType: state.visibilityFilter
})

const ProgressFooter = connect(
  mapStateToProps
)(Footer)

export default ProgressFooter
