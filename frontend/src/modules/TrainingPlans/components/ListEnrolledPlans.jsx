import React from "react";
import { useEffect, useState } from "react";
import TrainingPlanCard from "./TrainingPlanCard"
import NavigationBar from "../../home/components/NavigationBar";
import { getUserEnrolledPlans } from "../services/trainingService";
import Paginator from "../../../Paginator";

function ListEnrolledPlans() {

    const [plans,setPlans] = useState([])
    const [filteredPlans, setFilteredPlans] = useState([]);
    const [selectedDistance, setSelectedDistance] = useState("");
    const [selectedLevel, setSelectedLevel] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [plansPerPage] = useState(10); 

    const distances = ["5K", "10K", "21K", "42K"];
    const levels = ["Principiante", "Intermedio", "Avanzado"];

    useEffect(() => {
        const fetchData = async () => {
            const user = JSON.parse(localStorage.getItem("userAuth"))
            const trainersPlans = await getUserEnrolledPlans(user.id);
            setPlans(trainersPlans)
            setFilteredPlans(trainersPlans)
        }
        fetchData()
        console.log("planes: " + filteredPlans)
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
                    <option value="">Selecionar nivel</option>
                    {levels.map((level, index) => (
                        <option key={index} value={level}>{level}</option>
                    ))}
                </select>
            </div>) : (<div className='container'>
               <p className='text-custom'>
                NO ESTAS INSCRITO A NINGÚN PLAN, APUNTATE A ALGUNO!!</p>
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
                        numSessions={plan.numSessions}
                        sessionsCompleted={plan.sessionsCompleted}
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

export default ListEnrolledPlans;