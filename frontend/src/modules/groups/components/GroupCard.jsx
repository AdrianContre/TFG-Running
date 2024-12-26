import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faUser, faUserGroup} from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from "react-router";
import '../styles/groupCard.css'

function GroupCard({ id, name, trainerInfo }) {
    const navigate = useNavigate()

    const handleViewDetails = (event) => {
        event.preventDefault()
        navigate('/viewgroup', { state: {groupId: id}})
    }

    return (
        <div className="card group-card">
            <div className="card-body">
                <h5 className="card-title">
                <FontAwesomeIcon icon={faUserGroup} /> <strong className="custom-title">{name}</strong>
                </h5>
                <p className="card-text">
                    <FontAwesomeIcon icon={faUser} /> Entrenador: <strong>{trainerInfo}</strong>
                </p>
                <button className="details-button" onClick={handleViewDetails}>Ver Detalles</button>
            </div>
        </div>
    );
}


export default GroupCard;
