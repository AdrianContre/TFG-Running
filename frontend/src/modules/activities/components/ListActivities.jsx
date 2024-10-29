import NavigationBar from "../../home/components/NavigationBar";
import { useState,useEffect } from "react";
import { listActivities } from "../services/activities";
import '../styles/listActivities.css'
import ActivityTable from "./ActivityTable";


function ListActivities () {
    const [activities, setActivities] = useState([])
    const [show, setShow] = useState(false)
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
                    <ActivityTable activities={activities} />
                    
                    
                    
                </>
            )}

        </>
    )
}

export default ListActivities;