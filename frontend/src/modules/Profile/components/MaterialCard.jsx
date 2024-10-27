import React from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import '../styles/materialCard.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faPenToSquare, faTrash} from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from 'react-router';

const MaterialCard = ({ material, onDelete }) => {
    const navigate = useNavigate();
    const handleDelete = () => {
        onDelete(material.id)
    }
    const handleEdit = (event) => {
        event.preventDefault()
        navigate('/editmaterial', { state: { material: material } })
    }
    return (
        <Card className="material-card">
            <Card.Body>
                <Card.Title>Marca: {material.brand}</Card.Title>
                <Card.Text>Modelo: {material.model}</Card.Text>
                <Card.Text>Descripción: {material.description}</Card.Text>
                <Card.Text>Kilometraje: {material.wear} km</Card.Text>
                {/* <Button variant="success">Editar</Button>
                <Button variant="danger">Eliminar</Button> */}
                <div className="icons-container">
                    <FontAwesomeIcon icon={faPenToSquare} size="2xl" style={{cursor: 'pointer'}} onClick={handleEdit}/>
                    <FontAwesomeIcon icon={faTrash} size="2xl" style={{color: "#f10909", cursor: 'pointer'}} onClick={handleDelete} />
                </div>
            </Card.Body>
        </Card>
    );
};

export default MaterialCard;