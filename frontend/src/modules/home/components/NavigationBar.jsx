import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import '../styles/navbar.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCircleUser} from '@fortawesome/free-solid-svg-icons'
function NavigationBar () {
    return (
        <>
          <Navbar bg="light" data-bs-theme="light">
            <Container>
              <Navbar.Brand href="#home">Principal</Navbar.Brand>
              <Nav className="me-auto">
                <Nav.Link href="#home">Planes de entrenamiento generales</Nav.Link>
                <Nav.Link href="#features">Mis planes</Nav.Link>
                <Nav.Link href="#pricing">Grupos</Nav.Link>
                <Nav.Link href="#activities">Actividades</Nav.Link>
                {/* <Nav.Link href="#profile">Perfil</Nav.Link> */}
              </Nav>
              <Nav className="ms-auto">
                    <Nav.Link href="#profile">
                        <FontAwesomeIcon icon={faCircleUser} size="2xl" style={{color: "#000000"}} />
                    </Nav.Link>
              </Nav>
            </Container>
          </Navbar> 
        </>
      );
}

export default NavigationBar;