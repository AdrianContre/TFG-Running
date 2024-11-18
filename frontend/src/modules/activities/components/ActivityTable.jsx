import '../styles/activityTable.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faPenToSquare, faTrash, faEye} from '@fortawesome/free-solid-svg-icons'
import { deleteManualActivity, deleteResult } from '../services/activitiesService';
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
        else {
            const deleteAct = await deleteResult(activity.id)
            onDeleteActivity(activity.id)
        }
    }

    const handleViewActivity = (activity) => {
        if (activity.type === "ManualActivity") {
            navigate('/viewmanualactivity',{ state: { manualActivityId: activity.id } })
        }
        else if (activity.type === "RunningResult") {
            navigate('/viewrunningresult',{ state: { sessionId: activity.id } })
        }
        else if (activity.type === "StrengthResult") {
            navigate('/viewstrengthresult', { state: { sessionId: activity.id } })
        }

        else {
            navigate('/viewmobilityresult', { state: { sessionId: activity.id } })
        }

    }

    const handleEditActivity = (activity) => {
        if (activity.type === "ManualActivity") {
            navigate('/editmanualactivity', { state: {manualActivityId: activity.id}})
        }
        else if (activity.type === "RunningResult") {
            navigate('/editrunningresult', { state: {sesionId: activity.id}})
        }
        else {
            navigate('/editgenericresult', { state: {sesionId: activity.id, type: activity.type}})
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
                            {activity.distance === null ? (<td> - </td>) : (<td>{activity.distance}km</td>)}
                            {activity.duration === null ? (<td> - </td>) : (<td>{activity.duration}</td>)}
                            <td>{formatDate(activity.date)}</td>
                            
                            <td>
                                <input type="hidden" value={activity.id} />
                                <input type="hidden" value={activity.type} />
                                <div className="icons-activities-container">
                                    <FontAwesomeIcon icon={faEye} style={{color: "#005eff", cursor: 'pointer'}} onClick={() => {handleViewActivity(activity)}}/>
                                    <FontAwesomeIcon icon={faPenToSquare} style={{cursor: 'pointer'}} onClick={() => {handleEditActivity(activity)}}/>
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