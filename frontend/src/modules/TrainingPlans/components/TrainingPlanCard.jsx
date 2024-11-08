import React from "react";
import '../styles/trainingPlanCard.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faRunning, faMapMarkerAlt, faSignal, faUser} from '@fortawesome/free-solid-svg-icons'

function TrainingPlanCard({ name, objDistance, level, trainerName, trainerSurname }) {
    return (
        <div className="card training-plan-card">
            <div className="card-body">
                <h5 className="card-title">
                    <FontAwesomeIcon icon={faRunning} /> <strong className="custom-title">{name}</strong>
                </h5>
                <p className="card-text">
                    <FontAwesomeIcon icon={faMapMarkerAlt} /> Distancia objetivo: <strong>{objDistance}</strong>
                </p>
                <p className="card-text">
                    <FontAwesomeIcon icon={faSignal} /> Nivel: <strong>{level}</strong>
                </p>
                <p className="card-text">
                    <FontAwesomeIcon icon={faUser} /> Entrenador: <strong>{trainerName} {trainerSurname}</strong>
                </p>
                <button className="details-button">Ver Detalles</button>
            </div>
        </div>
    );
}


export default TrainingPlanCard;
