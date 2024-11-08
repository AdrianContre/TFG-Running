import React from "react"
import NavigationBar from "../../home/components/NavigationBar"
import CirclePlus from '../../../assets/images/plus-circle.png'
import '../styles/viewTrainingPlans.css'
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"
import { getMyPlans, getOtherUsersPlans } from "../services/trainingService"
import TrainingPlanCard from "./TrainingPlanCard"

function ViewMyPlans () {
    const navigate = useNavigate()
    const [plans,setPlans] = useState([])
    const [filteredPlans, setFilteredPlans] = useState([]);
    const [selectedDistance, setSelectedDistance] = useState("");
    const [selectedLevel, setSelectedLevel] = useState("");

    const distances = ["5K", "10K", "21K", "42K"];
    const levels = ["Principiante", "Intermedio", "Avanzado"];
    useEffect(() => {
        const fetchInfo = async () => {
            const user = JSON.parse(localStorage.getItem('userAuth'))
            const trainerId = user.id
            const trainersPlans = await getMyPlans(trainerId);
            setPlans(trainersPlans)
            setFilteredPlans(trainersPlans)
        }
        fetchInfo()
        
    },[])


    const handleFilterChange = () => {
        let filtered = plans;
        
        if (selectedDistance) {
            filtered = filtered.filter(plan => plan.objDistance === selectedDistance);
        }
        if (selectedLevel) {
            filtered = filtered.filter(plan => plan.level === selectedLevel);
        }
        
        setFilteredPlans(filtered);
    };

    useEffect(() => {
        handleFilterChange();
    }, [selectedDistance, selectedLevel]);


    return (
        <>
            <NavigationBar />
            <div className="filter-container">
                <select
                    value={selectedDistance}
                    onChange={(e) => setSelectedDistance(e.target.value)}
                    className="filter-select"
                >
                    <option value="">Select Distance</option>
                    {distances.map((distance, index) => (
                        <option key={index} value={distance}>{distance}</option>
                    ))}
                </select>
                
                <select
                    value={selectedLevel}
                    onChange={(e) => setSelectedLevel(e.target.value)}
                    className="filter-select"
                >
                    <option value="">Select Level</option>
                    {levels.map((level, index) => (
                        <option key={index} value={level}>{level}</option>
                    ))}
                </select>
            </div>
            <div className="plans-container">
                {filteredPlans.map((plan, index) => (
                    <TrainingPlanCard
                        key={index}
                        name={plan.name}
                        objDistance={plan.objDistance}
                        level={plan.level}
                        trainerName={plan.trainerName}
                        trainerSurname={plan.trainerSurname}
                    />
                ))}
            </div>
            
        </>
    )
}

export default ViewMyPlans;