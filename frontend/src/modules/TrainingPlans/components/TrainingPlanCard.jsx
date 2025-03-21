import React from "react";
import '../styles/trainingPlanCard.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faRunning, faMapMarkerAlt, faSignal, faUser,faCheckCircle, faShirt} from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from "react-router";

function TrainingPlanCard({ id, name, objDistance, level, trainerName, trainerSurname, numSessions, sessionsCompleted, wearMaterial }) {
    const navigate = useNavigate()
    console.log("wear material: " +wearMaterial)

    const handleViewDetails = (event) => {
        event.preventDefault()
        navigate('/viewplan', { state: {planId: id}})
    }

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
                    <FontAwesomeIcon icon={faShirt} /> Desgaste de material: <strong style={{marginLeft: '3px'}}>{wearMaterial === "low" ? (" < 300km") : (wearMaterial === "medium" ? ('300-500km') : ('> 500km'))}</strong>
                </p>
                <p className="card-text">
                    <FontAwesomeIcon icon={faUser} /> Entrenador: <strong>{trainerName} {trainerSurname}</strong>
                </p>
                {numSessions ? (
                    <p className="card-text">
                    <FontAwesomeIcon icon={faCheckCircle} /> Sesiones completadas: <strong >{sessionsCompleted}/{numSessions}</strong>
                </p>
                ) : (
                    <>
                    </>
                )}
                <button className="details-button" onClick={handleViewDetails}>Ver Detalles</button>
            </div>
        </div>
    );
}


export default TrainingPlanCard;
