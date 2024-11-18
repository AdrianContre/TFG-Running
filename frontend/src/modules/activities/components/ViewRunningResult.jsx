import React, { useEffect, useState } from "react";
import { useLocation } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import { getManualActivity, getRunningResult } from "../services/activitiesService";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import GpxViewer from "./GpxViewer";
import "../styles/viewManualActivity.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faDownload, faSmile, faFrown, faMeh} from '@fortawesome/free-solid-svg-icons'

function ViewRunningResult () {
    const location = useLocation();
    const { sessionId } = location.state;
    const [activity, setActivity] = useState(null);
    const [gpxUrl, setGpxUrl] = useState(null);

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
            const session = await getRunningResult(sessionId);
            console.log(session.data.pace.toFixed(2))
            setActivity(session.data);

            if (session.data.route !== null) {
                const route = session.data.route;
                const byteCharacters = atob(route);
                const byteNumbers = new Uint8Array(byteCharacters.length);

                for (let i = 0; i < byteCharacters.length; i++) {
                    byteNumbers[i] = byteCharacters.charCodeAt(i);
                }

                const blob = new Blob([byteNumbers], { type: 'application/gpx+xml' });
                const url = URL.createObjectURL(blob);
                setGpxUrl(url);
            }
        };
        fetchActivity();
    }, []);

    return (
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
                                <div>
                                    <strong>Distancia</strong>
                                    <p>{activity.distance} km</p>
                                </div>
                                <div>
                                    <strong>Duración</strong>
                                    <p>{activity.duration}</p>
                                </div>
                                <div>
                                    <strong>Ritmo medio</strong>
                                    <p>{activity.pace.toFixed(2)} min/km</p>
                                </div>
                                <div>
                                    <strong>Frec. cardiaca media</strong>
                                    <p>{activity.fcAvg} ppm</p>
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
                
                {gpxUrl && (
                    <MapContainer className="view-activity-map" center={[0, 0]} zoom={13}>
                        <a
                        href={gpxUrl}
                        download={`${activity.name}.gpx`}
                        className="download-button"
                        >
                            <FontAwesomeIcon icon={faDownload} style={{color: "#000000", marginRight: '5px'}} />
                            GPX
                        </a>

                        <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
                        <GpxViewer gpxUrl={gpxUrl} />
                    </MapContainer>
                )}
            </div>
        </>
    )
}

export default ViewRunningResult;