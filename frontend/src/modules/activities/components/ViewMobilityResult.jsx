import React, { useEffect, useState } from "react";
import { useLocation } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import "../styles/viewManualActivity.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faSmile, faFrown, faMeh} from '@fortawesome/free-solid-svg-icons'
import { getMobilityResult } from "../services/activitiesService";
import { Spinner } from "react-bootstrap";

function ViewMobilityResult () {
    const location = useLocation();
    const { sessionId } = location.state;
    const [activity, setActivity] = useState(null);

    const formatDate = (dateString) => {
        const options = { weekday: 'short', year: 'numeric', month: 'numeric', day: 'numeric' };
        const date = new Date(dateString);
        let formattedDate = date.toLocaleDateString('es-ES', options).replace(" ", ''); 
        let finalDate = `${formattedDate.slice(0,3)}.,${formattedDate.slice(4)}`;
        return finalDate;
    };

    const ratings = [
        { value: 1, label: "Fatal", icon: faFrown },
        { value: 2, label: "Mal", icon: faFrown },
        { value: 3, label: "Regular", icon: faMeh },
        { value: 4, label: "Bien", icon: faSmile },
        { value: 5, label: "Estupendamente", icon: faSmile },
    ];

    useEffect(() => {
        const fetchActivity = async () => {
            const session = await getMobilityResult(sessionId);
            setActivity(session.data);
        };
        fetchActivity();
    }, []);

    if (!activity) {
        return (
            <div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                <Spinner animation="border" role="status"/>
            </div>
        )
    }


    return  (
        <>
             <NavigationBar />
            <div className="view-activity-container">
                {activity && (
                    <>
                        <h2 className="view-activity-title">{activity.planName}: {activity.name}</h2>
                        <div className="view-activity-card">
                            <div className="view-activity-data">
                                <div>
                                    <strong>Fecha</strong>
                                    <p>{formatDate(activity.date)}</p>
                                </div>
                            </div>
                            <div className="view-activity-description">
                                <strong>Sensaciones:</strong> 
                                <div style={{width: '50%'}} className="rating-container">
                                    {ratings.map((rating) => (
                                        <div
                                            key={rating.value}
                                            className={`rating-icon ${activity.effort === rating.value ? 'selected' : ''}`}
                                        >
                                            <FontAwesomeIcon icon={rating.icon} />
                                            <p>{rating.label}</p>
                                        </div>
                                    ))}
                                </div>
                                <strong>Descripción:</strong><p>{activity.description}</p> 
                                <p><strong>Materiales empleados:</strong></p>
                                <ul className="view-activity-materials-list">
                                    {activity.materials.map((material, index) => (
                                        <li key={index}>{material}</li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                    </>
                )}
            </div>
        </>
    )
}

export default ViewMobilityResult;