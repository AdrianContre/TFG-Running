import { useEffect } from "react";
import { useState } from "react";
import NavigationBar from "../../home/components/NavigationBar";
import '../styles/listGroup.css'
import { getAvailableGroups } from "../services/groupService";
import GroupCard from "./GroupCard";
import Paginator from "../../../Paginator";
import { Spinner } from "react-bootstrap";
import { useSelector } from "react-redux";

function ListGroups() {
    const [groups, setGroups] = useState(null)
    const [isTrainer, setIsTrainer] = useState(false)
    const [currentPage, setCurrentPage] = useState(1);
    const [groupsPerPage] = useState(10); 
    const user = useSelector((state) => {return state.auth.user})
    useEffect(() => {
        const fetchInfo = async () => {
            if (user.userType === "Trainer") {
                setIsTrainer(true)
            }

            const groups = await getAvailableGroups()
            setGroups(groups.data)
        }
        fetchInfo()
    },[])

    if (!groups) {
        return (
            <div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                <Spinner animation="border" role="status"/>
            </div>
        )
    }

    const totalPages = Math.ceil(groups.length / groupsPerPage);
    const indexOfLastPlan = currentPage * groupsPerPage;
    const indexOfFirstPlan = indexOfLastPlan - groupsPerPage;
    const currentGroups = groups.slice(indexOfFirstPlan, indexOfLastPlan);
    

    return (
        <>

            <NavigationBar />
            <div className="groups-container">
                {currentGroups.length > 0 ? (currentGroups.map((group, index) => (
                    <GroupCard
                        key={index}
                        id={group.id}
                        name={group.name}
                        trainerInfo={group.trainerName}
                    />
                ))) : (<div className='container'>
                <p className='text-custom'>NO ERES MIEMBRO DE NINGÚN GRUPO</p></div>)}
            </div>
            <Paginator 
                currentPage={currentPage} 
                totalPages={totalPages} 
                onPageChange={setCurrentPage} 
            />
        </>
    )
}


export default ListGroups;