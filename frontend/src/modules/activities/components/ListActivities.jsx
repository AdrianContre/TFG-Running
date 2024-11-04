import NavigationBar from "../../home/components/NavigationBar";
import { useState,useEffect } from "react";
import { listActivities } from "../services/activitiesService";
import '../styles/listActivities.css'
import ActivityTable from "./ActivityTable";
import CirclePlus from '../../../assets/images/plus-circle.png'
import { useNavigate } from "react-router";



function ListActivities () {
    const [activities, setActivities] = useState([])
    const [show, setShow] = useState(false)
    const navigate = useNavigate()
    useEffect(() => {
        const getActivities = async () => {
            const runnerId = JSON.parse(localStorage.getItem("userAuth")).id
            const queryActivities = await listActivities(runnerId)
            if (queryActivities.error) {
                setShow(true)
            }
            else { 
                setActivities(queryActivities.data)
            }
        }
        getActivities()
    },[])

    const handleClick = (event) => {
        event.preventDefault();
        navigate('/createactivity')
    }

    const handleDeleteActivity = (deletedActivityId) => {
        setActivities(activities.filter(activity => activity.id !== deletedActivityId))
        
    };
    return (
        <>
            <NavigationBar />
            {show == true ? (
                <p>Empieza a crear actividades</p>
            ) : (
                <>
                    <div className="list-activities-container">
                        <h1>Actividades recientes:</h1>
                    </div>
                    <ActivityTable activities={activities} onDeleteActivity={handleDeleteActivity}/>
                </>
            )}
            <div className="image-container">
                <div className="circle-container">
                    <img className="circle-image" src={CirclePlus} onClick={handleClick}/>
                    <div className="tooltip-text">Añadir actividad</div>
                </div>
            </div> 
        </>
    )
}

export default ListActivities;