import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

function PopUp ({error,show, onHide}) {
    return (
        <Modal show={show} onHide={onHide} backdrop="static" keyboard={false}>
            <Modal.Header closeButton>
            <Modal.Title>Error al registrarse</Modal.Title>
            </Modal.Header>
            <Modal.Body>
            {error}
            </Modal.Body>
            <Modal.Footer>
            <Button variant="primary" onClick={onHide}>
            OK
            </Button>
            </Modal.Footer>
        </Modal>
    )
}

export default PopUp;