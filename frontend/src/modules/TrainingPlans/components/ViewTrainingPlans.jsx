import React from "react"
import NavigationBar from "../../home/components/NavigationBar"
import '../styles/viewTrainingPlans.css'
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"
import { getOtherUsersPlans } from "../services/trainingService"
import TrainingPlanCard from "./TrainingPlanCard"
import Paginator from "../../../Paginator"
import { Spinner } from "react-bootstrap"

function ViewTrainingPlans () {
    const navigate = useNavigate()
    const [isTrainer, setIsTrainer] = useState(null)
    const [plans,setPlans] = useState(null)
    const [filteredPlans, setFilteredPlans] = useState([]);
    const [selectedDistance, setSelectedDistance] = useState("");
    const [selectedLevel, setSelectedLevel] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [plansPerPage] = useState(10); 

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
    
    const handleFilterChange = () => {
        let filtered = plans;
        
        if (selectedDistance) {
            filtered = filtered.filter(plan => plan.objDistance === selectedDistance);
        }
        if (selectedLevel) {
            filtered = filtered.filter(plan => plan.level === selectedLevel);
        }
        
        setFilteredPlans(filtered);
        setCurrentPage(1);
    };

    useEffect(() => {
        handleFilterChange();
    }, [selectedDistance, selectedLevel]);

    if (!plans) {
        return (
            <div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                <Spinner animation="border" role="status"/>
            </div>
        )
    }

    const totalPages = Math.ceil(filteredPlans.length / plansPerPage);
    const indexOfLastPlan = currentPage * plansPerPage;
    const indexOfFirstPlan = indexOfLastPlan - plansPerPage;
    const currentPlans = filteredPlans.slice(indexOfFirstPlan, indexOfLastPlan);


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

            <div className="plans-container">
                {currentPlans.map((plan, index) => (
                    <TrainingPlanCard
                        key={index}
                        id={plan.id}
                        name={plan.name}
                        objDistance={plan.objDistance}
                        level={plan.level}
                        trainerName={plan.trainerName}
                        trainerSurname={plan.trainerSurname}
                        wearMaterial={plan.wearMaterial}
                    />
                ))}
            </div>
            <Paginator 
                currentPage={currentPage} 
                totalPages={totalPages} 
                onPageChange={setCurrentPage} 
            />
            
        </>
    )
}

export default ViewTrainingPlans;