import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { NavLink } from 'react-router-dom';
import '../styles/navbar.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCircleUser} from '@fortawesome/free-solid-svg-icons'
import Dropdown from 'react-bootstrap/Dropdown';
import { useState, useEffect } from 'react';


function NavigationBar () {
  const [profilePicture, setProfilePicture] = useState(null)
  const [userRole, setUserRole] = useState("")
  const handleClick = (event) => {
    localStorage.removeItem('token')
    localStorage.removeItem('userAuth')
  }

  useEffect (() => {
    const user = JSON.parse(localStorage.getItem('userAuth'))
    if (user.profilePicture !== null) {
      console.log("entro aqui")
      setProfilePicture(`data:image;base64,${user.profilePicture}`)
    }
    if (user.userType === "Trainer") {
      setUserRole("trainer")
    }
    else {
      setUserRole("runner")
    }
  },[])
    return (
        <>
          <Navbar bg="light" data-bs-theme="light">
            <Container>
              <Navbar.Brand as={NavLink} to="/main">Principal</Navbar.Brand>
              <Nav className="me-auto">
                <Nav.Link as={NavLink} to='/activities'>Actividades</Nav.Link>
                <Nav.Link as={NavLink} to='/trainingplans'>Planes de entrenamiento</Nav.Link>
                <Nav.Link href="#groupplans">Planes grupales</Nav.Link>
                {userRole === "trainer" ? (
                  <Nav.Link as={NavLink} to='/myplans'>Mis planes</Nav.Link>
                ) : (
                  <></>
                )}
                <Nav.Link as={NavLink} to='/enrolledplans'>Planes en curso</Nav.Link>
                <Nav.Link as={NavLink} to='/groups'>Grupos</Nav.Link>
                {userRole === "trainer" ? (
                  <Nav.Link as={NavLink} to='/mygroups'>Mis grupos</Nav.Link>
                ) : (
                  <></>
                )}
              </Nav>
              <Nav className="ms-auto">
                <Dropdown align="end">
                    <Dropdown.Toggle as="div" id="dropdown-basic" style={{ cursor: 'pointer' }}>
                      {profilePicture !== null ? (
                        <img src={profilePicture} alt="profile" className='edit-profile-picture' style={{ width: '32px', height: '32px' }} />
                      ) : (
                        <FontAwesomeIcon icon={faCircleUser} size="2xl" style={{color: "#000000"}} />
                      )}   
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