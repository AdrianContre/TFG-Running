import { useEffect } from "react";
import { useState } from "react";
import NavigationBar from "../../home/components/NavigationBar";
import CirclePlus from '../../../assets/images/plus-circle.png'
import { useNavigate } from "react-router";
import '../styles/listGroup.css'
import {getTrainerGroups } from "../services/groupService";
import GroupCard from "./GroupCard";
import Paginator from "../../../Paginator";
import { Spinner } from "react-bootstrap";
import { useSelector } from "react-redux";

function ListMyGroups() {
    const [groups, setGroups] = useState(null)
    const [isTrainer, setIsTrainer] = useState(false)
    const [currentPage, setCurrentPage] = useState(1);
    const [groupsPerPage] = useState(10); 
    const navigate = useNavigate()
    const user = useSelector((state) => {return state.auth.user})
    useEffect(() => {
        const fetchInfo = async () => {
            if (user.userType === "Trainer") {
                setIsTrainer(true)
            }
            const groups = await getTrainerGroups(user.id)
            setGroups(groups.data)
        }
        fetchInfo()
    },[])

    const handleClick = (event) => {
        event.preventDefault()
        navigate('/creategroup')
    }

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
                <p className='text-custom'>NO HAS CREADO NINGÚN GRUPO, CREA UNO DESDE EL ICONO DE ABAJO!!</p></div>)}
            </div>
            <Paginator 
                currentPage={currentPage} 
                totalPages={totalPages} 
                onPageChange={setCurrentPage} 
            />
            {isTrainer === true ? (<div className="image-container">
                <div className="circle-container">
                    <img className="circle-image" src={CirclePlus} onClick={handleClick}/>
                    <div className="tooltip-text">Crear grupo</div>
                </div>
            </div>) : (null)}
        </>
    )
}


export default ListMyGroups;