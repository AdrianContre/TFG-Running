import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { NavLink } from 'react-router-dom';
import '../styles/navbar.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCircleUser} from '@fortawesome/free-solid-svg-icons'
import Dropdown from 'react-bootstrap/Dropdown';


function NavigationBar () {
  const handleClick = (event) => {
    localStorage.removeItem('token')
  }
    return (
        <>
          <Navbar bg="light" data-bs-theme="light">
            <Container>
              <Navbar.Brand as={NavLink} to="/main">Principal</Navbar.Brand>
              <Nav className="me-auto">
                <Nav.Link href="#plans">Planes de entrenamiento generales</Nav.Link>
                <Nav.Link href="#MyPlans">Mis planes</Nav.Link>
                <Nav.Link href="#groups">Grupos</Nav.Link>
                <Nav.Link href="#activities">Actividades</Nav.Link>
              </Nav>
              {/* <Nav className="ms-auto">
                    <Nav.Link as={NavLink} to="/profile">
                        <FontAwesomeIcon icon={faCircleUser} size="2xl" style={{color: "#000000"}} />
                    </Nav.Link>
              </Nav> */}
              <Nav className="ms-auto">
                <Dropdown align="end">
                    <Dropdown.Toggle as="div" id="dropdown-basic" style={{ cursor: 'pointer' }}>
                        <FontAwesomeIcon icon={faCircleUser} size="2xl" style={{color: "#000000"}} />
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        <Dropdown.Item as={NavLink} to="/profile">Perfil</Dropdown.Item>
                        <Dropdown.Item as={NavLink} to="/editprofile">Editar perfil</Dropdown.Item>
                        <Dropdown.Item as={NavLink} onClick={handleClick} to="/">Cerrar sesión</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
              </Nav>
            </Container>
          </Navbar> 
        </>
      );
}

export default NavigationBar;