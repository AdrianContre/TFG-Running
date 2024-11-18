import React from "react"
import NavigationBar from "../../home/components/NavigationBar"
import CirclePlus from '../../../assets/images/plus-circle.png'
import '../styles/viewTrainingPlans.css'
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"
import { getOtherUsersPlans } from "../services/trainingService"
import TrainingPlanCard from "./TrainingPlanCard"

function ViewTrainingPlans () {
    const navigate = useNavigate()
    const [isTrainer, setIsTrainer] = useState(null)
    const [plans,setPlans] = useState([])
    const [filteredPlans, setFilteredPlans] = useState([]);
    const [selectedDistance, setSelectedDistance] = useState("");
    const [selectedLevel, setSelectedLevel] = useState("");

    const distances = ["5K", "10K", "21K", "42K"];
    const levels = ["Principiante", "Intermedio", "Avanzado"];
    useEffect(() => {
        const fetchInfo = async () => {
            const user = JSON.parse(localStorage.getItem('userAuth'))
            if (user.userType === "Runner") {
                setIsTrainer(false)
            }
            else {
                setIsTrainer(true)
            }
            const trainersPlans = await getOtherUsersPlans();
            setPlans(trainersPlans)
            setFilteredPlans(trainersPlans)
        }
        fetchInfo()
        
    },[])
    const handleClick = (event) => {
        event.preventDefault();
        navigate('/createtrainingplans')
    }


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
            {plans.length > 0 ? (
                <div className="filter-container">
                <select
                    value={selectedDistance}
                    onChange={(e) => setSelectedDistance(e.target.value)}
                    className="filter-select"
                >
                    <option value="">Seleccionar distancia</option>
                    {distances.map((distance, index) => (
                        <option key={index} value={distance}>{distance}</option>
                    ))}
                </select>
                
                <select
                    value={selectedLevel}
                    onChange={(e) => setSelectedLevel(e.target.value)}
                    className="filter-select"
                >
                    <option value="">Seleccionar nivel</option>
                    {levels.map((level, index) => (
                        <option key={index} value={level}>{level}</option>
                    ))}
                </select>
            </div>
            ) : (<div className='container'>
            <p className='text-custom'>
             NO HAY PLANES DISPONIBLES</p>
         </div>)}
            

            {isTrainer ? (
                <div className="image-container">
                    <div className="circle-container">
                        <img className="circle-image" src={CirclePlus} onClick={handleClick}/>
                        <div className="tooltip-text-view-training-plans">Crear plan de entrenamiento</div>
                    </div>
                </div> 
            ): (
                <>
                </>
            )}
            <div className="plans-container">
                {filteredPlans.map((plan, index) => (
                    <TrainingPlanCard
                        key={index}
                        id={plan.id}
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

export default ViewTrainingPlans;