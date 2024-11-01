import React from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import '../styles/materialCard.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faPenToSquare, faTrash} from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from 'react-router';
import { useEffect, useState } from 'react';
import ProgressBar from './ProgressBar';

const MaterialCard = ({ material, onDelete }) => {
    const navigate = useNavigate();
    const handleDelete = () => {
        onDelete(material.id)
    }
    const handleEdit = (event) => {
        event.preventDefault()
        navigate('/editmaterial', { state: { material: material } })
    }

    const [picture, setPicture] = useState(null)

    useEffect(() => {
        const getPicture = () => {
            if (material.photo !== null) {
                setPicture(`data:image/jpeg;base64,${material.photo}`)
            }
        }
        getPicture()
    },[])
    return (
        <Card className="material-card">
            <Card.Body>
                <Card.Title>Marca: {material.brand}</Card.Title>
                <Card.Text>Modelo: {material.model}</Card.Text>
                <Card.Text>Descripción: {material.description}</Card.Text>
                <Card.Text>Kilometraje: {material.wear} km</Card.Text>
                <ProgressBar current={material.wear} limit={700} />
                {picture !== null ? (
                    <Card.Img style={{height: '300px', marginBottom: '20px'}} src={picture} />
                ) : <></>}
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