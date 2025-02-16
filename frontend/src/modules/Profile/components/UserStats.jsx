import { useState } from "react";
import { useEffect } from "react";
import { Button, Spinner } from "react-bootstrap";
import { getRunnerStats, getTrainerStats } from "../services/profileService";
import LineChart from "./LineChart";
import PieChart from "./PieChart";
import '../styles/userStats.css'
import { useSelector } from "react-redux";


function UserStats() {
    const [runnerData, setRunnerData] = useState(null)
    const [trainerData, setTrainerData] = useState(null)
    const user = useSelector((state) => {return state.auth.user})
    
    useEffect(()=> {
        const fetchInfo = async () => {
            const runnerStats = await getRunnerStats(user.id)
            setRunnerData(runnerStats.data)

            if (user.userType === "Trainer") {
                const trainerStats = await getTrainerStats(user.id)
                setTrainerData(trainerStats.data)
            }
            
        }
        fetchInfo()
    },[])
    
    if (!runnerData && !trainerData) {
        return (
            <div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                <Spinner animation="border" role="status"/>
            </div>
        )
    }

    return (
        <div className="stats-container">
            <h2>Estadísticas:</h2>
            <p><strong>Resumen semanal: </strong>{runnerData.weekActivities} {runnerData.weekActivities === 1 ? "actividad" : "actividades"} y {runnerData.weekMileage}Km totales</p>
            <p><strong>Número de planes en curso: </strong>{runnerData.plansInProgress}</p>
            {runnerData.mostUsedMaterial ? (
                <p className="material-info"><strong>Material más utilizado: </strong>{Object.keys(runnerData.mostUsedMaterial)[0]} con {runnerData.mostUsedMaterial[Object.keys(runnerData.mostUsedMaterial)[0]]}km</p>
            ) : (null)}
            {user.userType === "Trainer" ? (
                <p><strong>Número de usuarios inscritos en mis planes: </strong>{trainerData}</p>
            ) : (null)}
            <div className="chart-container">
                <LineChart last4Weeks={runnerData.last4Weeks} />
                {runnerData.distribution.every(item => item === 0) ? (null) : <PieChart distribution={runnerData.distribution} />}
                
            </div>
        </div>
    )
}

export default UserStats;