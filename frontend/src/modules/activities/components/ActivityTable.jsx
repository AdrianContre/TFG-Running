import '../styles/activityTable.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faPenToSquare, faTrash, faEye} from '@fortawesome/free-solid-svg-icons'
import { deleteManualActivity } from '../services/activitiesService';
import { useNavigate } from 'react-router';

function ActivityTable ({activities, onDeleteActivity}) {

    const formatDate = (dateString) => {
        const options = { weekday: 'short', year: 'numeric', month: 'numeric', day: 'numeric' };
        const date = new Date(dateString);
       
        
       
        let formattedDate = date.toLocaleDateString('es-ES', options).replace(" ", ''); 
    
        
        let finalDate = `${formattedDate.slice(0,3)}.,${formattedDate.slice(4)}`;
        return finalDate;
    };

    const navigate = useNavigate()

    const handleDeleteAct = async (activity) => {
        if (activity.type === "ManualActivity") {
            const deleteAct = await deleteManualActivity(activity.id)
            onDeleteActivity(activity.id)
        }
    }

    const handleViewActivity = (activity) => {
        if (activity.type === "ManualActivity") {
            navigate('/viewmanualactivity',{ state: { manualActivityId: activity.id } })
        }
    }

    return (
        <div className='table-container'>
            <table className="table">
                <thead className="table-light">
                    <tr>
                        <th>Nombre de la Actividad</th>
                        <th>Distancia</th>
                        <th>Duración</th>
                        <th>Fecha</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {activities.map((activity) => (
                        <tr key={activity.id} style={{ borderBottom: 'none' }}>
                            <td>{activity.name}</td>
                            <td>{activity.distance}km</td>
                            <td>{activity.duration}</td>
                            <td>{formatDate(activity.date)}</td>
                            <td>
                                {/* ID y tipo de actividad en campos ocultos */}
                                <input type="hidden" value={activity.id} />
                                <input type="hidden" value={activity.type} />
                                <div className="icons-activities-container">
                                    <FontAwesomeIcon icon={faEye} style={{color: "#005eff", cursor: 'pointer'}} onClick={() => {handleViewActivity(activity)}}/>
                                    <FontAwesomeIcon icon={faPenToSquare} style={{cursor: 'pointer'}} />
                                    <FontAwesomeIcon icon={faTrash} style={{color: "#f10909", cursor: 'pointer'}} onClick={() => {handleDeleteAct(activity)}} />
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ActivityTable;