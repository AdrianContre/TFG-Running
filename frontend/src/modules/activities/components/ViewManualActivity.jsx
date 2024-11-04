import React, { useEffect, useState } from "react";
import { useLocation } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import { getManualActivity } from "../services/activitiesService";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import GpxViewer from "./GpxViewer";
import "../styles/viewManualActivity.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faDownload} from '@fortawesome/free-solid-svg-icons'

function ViewManualActivity() {
    const location = useLocation();
    const { manualActivityId } = location.state;
    const [activity, setActivity] = useState(null);
    const [gpxUrl, setGpxUrl] = useState(null);

    const formatDate = (dateString) => {
        const options = { weekday: 'short', year: 'numeric', month: 'numeric', day: 'numeric' };
        const date = new Date(dateString);
        let formattedDate = date.toLocaleDateString('es-ES', options).replace(" ", ''); 
        let finalDate = `${formattedDate.slice(0,3)}.,${formattedDate.slice(4)}`;
        return finalDate;
    };

    useEffect(() => {
        const fetchManualActivity = async () => {
            const mAct = await getManualActivity(manualActivityId);
            setActivity(mAct.data);

            if (mAct.data.route !== null) {
                const route = mAct.data.route;
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
        fetchManualActivity();
    }, []);

    return (
        <>
            <NavigationBar />
            <div className="view-activity-container">
                {activity && (
                    <>
                        <h2 className="view-activity-title">{activity.name}</h2>
                        
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
                                    <p>{activity.pace} min/km</p>
                                </div>
                                <div>
                                    <strong>Frec. cardiaca media</strong>
                                    <p>{activity.fcAvg} ppm</p>
                                </div>
                            </div>
                            <div className="view-activity-description">
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
    );
}

export default ViewManualActivity;
