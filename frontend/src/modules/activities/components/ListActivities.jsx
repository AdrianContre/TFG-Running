import NavigationBar from "../../home/components/NavigationBar";
import { useState,useEffect } from "react";
import { listActivities } from "../services/activitiesService";
import '../styles/listActivities.css'
import ActivityTable from "./ActivityTable";
import CirclePlus from '../../../assets/images/plus-circle.png'
import { useNavigate } from "react-router";
import Paginator from "../../../Paginator";
import { Spinner } from "react-bootstrap";
import { useSelector } from "react-redux";


function ListActivities () {
    const [activities, setActivities] = useState(null)
    const [currentPage, setCurrentPage] = useState(1);
    const [activitiesPerPage] = useState(12);
    const [show, setShow] = useState(false)
    const navigate = useNavigate()
    const runnerId = useSelector((state) => { return state.auth.user.id})
    useEffect(() => {
        const getActivities = async () => {
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

    if (!activities) {
        return (
            <div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                <Spinner animation="border" role="status"/>
            </div>
        )
    }

    const totalPages = Math.ceil(activities.length / activitiesPerPage);
    const indexOfLastActivity = currentPage * activitiesPerPage;
    const indexOfFirstActivity = indexOfLastActivity - activitiesPerPage;
    const currentActivities = activities.slice(indexOfFirstActivity, indexOfLastActivity);

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
                    <ActivityTable activities={currentActivities} onDeleteActivity={handleDeleteActivity}/>
                    <Paginator
                        currentPage={currentPage}
                        totalPages={totalPages}
                        onPageChange={setCurrentPage}
                    />
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