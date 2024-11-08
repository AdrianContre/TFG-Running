import React from 'react';
import { Modal, Button } from 'react-bootstrap';

const ModalSession = ({ show, handleClose, session }) => {
  if (!session) return null; 
  let map = {}
  map["running"] = "Carrera"
  map["strength"] = "Fuerza"
  map["mobility"] = "Mobilidad"

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header style={{justifyContent: 'center'}}>
        <Modal.Title>{session.name}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p><strong>Tipo de entrenamiento:</strong> {map[session.type]}</p>
        {session.type === 'running' && (
            <>
                <p><strong>Tipo de sesión:</strong> {session.runningType}</p>
                <p><strong>Distancia:</strong> {session.distance} km</p>
                <p><strong>Duración:</strong> {session.duration}</p>
            </>
            )}
            <p style={{ whiteSpace: 'pre-line' }}><strong>Descripción:</strong> {session.description}</p>
        
      </Modal.Body>
      <Modal.Footer style={{justifyContent: 'center'}}>
        <Button variant="primary" onClick={handleClose}>
          Cerrar
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ModalSession;
