import React from "react";
import NavigationBar from "../../home/components/NavigationBar";
import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router";
import { enrollUserToPlan, getPlanInfo, unenrollUserToPlan } from "../services/trainingService";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faRunning, faMapMarkerAlt, faSignal, faUser, faCheck, faPlus} from '@fortawesome/free-solid-svg-icons';
import ModalSession from "./ModalSession";
import { Button } from "react-bootstrap";
import { getUserResultsPlan } from "../services/trainingResultService";


function ViewDetails () {
    const navigate = useNavigate()
    const [planData, setPlan] = useState({})
    const location = useLocation()
    const {planId} = location.state
    const [name, setName] = useState("")
    const [description, setDescription] = useState("")
    const [numWeeks, setNumWeeks] = useState(0)
    const [objDistance, setObjDistance] = useState("")
    const [level, setLevel] = useState("")
    const [trainingWeeks, setTrainingWeeks] = useState([])
    const [trainer, setTrainer] = useState({})
    const [sessionsInfo, setSessionsInfo] = useState(Array(numWeeks).fill(Array(7).fill(null)));
    const [showModal, setShowModal] = useState(false);
    const [selectedSession, setSelectedSession] = useState(null);
    const [hoveredSession, setHoveredSession] = useState({ weekIndex: null, dayIndex: null });
    const [userAuth, setUserAuth] = useState({})
    const[isEnrolled, setIsEnrolled] = useState(false)
    const [hasResult, setHasResult] = useState(Array(numWeeks).fill(Array(7).fill(false)));
    const [change,setChange] = useState(false)





  

    useEffect(() => {
        const fetchPlan = async () => {
            const planInfo = await getPlanInfo(planId)
            setPlan(planInfo)
            setName(planInfo.name)
            setDescription(planInfo.description)
            setNumWeeks(planInfo.numWeeks)
            setObjDistance(planInfo.objDistance)
            setLevel(planInfo.level)
            setTrainer(planInfo.creator)
            setTrainingWeeks(planInfo.trainingWeeks)
            setSessionsInfo(Array(numWeeks).fill(Array(7).fill(null)))
            setIsEnrolled(planInfo.enrolled)
            //setHasResult((Array(numWeeks).fill(Array(7).fill(null))))
            
            const user = JSON.parse(localStorage.getItem("userAuth"))
            setUserAuth(user)

            
            const weeklySessionsMatrix = planInfo.trainingWeeks.map(week => {
                let sessionsForWeek = Array(7).fill(null);
                week.sessions.forEach((session, index) => {
                    sessionsForWeek[index] = session;
                });
            
                return sessionsForWeek; 
            });
            console.log("sessiones: "+ weeklySessionsMatrix)
            setSessionsInfo(weeklySessionsMatrix)

            if (planInfo.enrolled) {
                const results = await getUserResultsPlan(planInfo.id, user.id)
                const hasResultMatrix = Array.from({ length: planInfo.numWeeks }, (_, i) =>
                    Array.from({ length: 7 }, (_, dayIndex) => results[i] ? results[i][dayIndex] : false)
                );
                
                setHasResult(hasResultMatrix);
                //setHasResult(results)
                
            }
            // console.log("resultado: " + hasResult[0][1])
        }
        fetchPlan()
    },[change])

    const viewSession = (session) => {
        console.log(session)
        setSelectedSession(session);
        setShowModal(true);
    }

    const handleClose = () => {
        setShowModal(false);
        setSelectedSession(null);
    };

    const handleEnroll = async (event) => {
        event.preventDefault()
        try {
            const fetch = await enrollUserToPlan(planId, userAuth.id)
            setChange(!change)
        }
        catch (error) {
            alert(error)
        }
        
    }

    const handleUnenroll = async (event) => {
        event.preventDefault()
        try {
            const fetch = await unenrollUserToPlan(planId, userAuth.id)
            navigate('/trainingplans')
        }
        catch(error) {
            alert(error)
        }
    }

    const handleEdit = (event) => {
        event.preventDefault()
        navigate('/editplan', { state: {planId: planId}})
    }

    const handleCreateSessionResult = (session) => {
        console.log(session)
        console.log(planData)
        if (session.type === "running") {
            navigate('/createrunningsessionresult', { state: {plan: planData, session: session}})
        }
        else {
            navigate('/createsessionresult', { state: {plan: planData, session: session}})
        }
    }

    const renderTrainingRows = () => {
        console.log(sessionsInfo)
        console.log("results: " + hasResult)
        let rows = [];
        for (let i = 0; i < numWeeks; i++) {
            rows.push(
                <div key={i} className="week-row-create-plan">
                    {[...Array(7)].map((_, dayIndex) => (
                        <div key={dayIndex} className="day-column-create-plan">
                            {sessionsInfo[i] && sessionsInfo[i][dayIndex] ? (
                                <div className="session-cell">
                                   <span
                                    className="session-name"
                                    style={{
                                        cursor: 'pointer',
                                        color: hoveredSession.weekIndex === i && hoveredSession.dayIndex === dayIndex ? 'blue' : 'initial'
                                    }}
                                    onMouseEnter={() => setHoveredSession({ weekIndex: i, dayIndex })}
                                    onMouseLeave={() => setHoveredSession({ weekIndex: null, dayIndex: null })}
                                    onClick={(event) => viewSession(sessionsInfo[i][dayIndex])}
                                    >
                                    {sessionsInfo[i][dayIndex].name}
                                    </span>
                                    {sessionsInfo[i][dayIndex].type !== "rest" ? (numWeeks === 1 ? (
                                        trainer.id !== userAuth.id ? (
                                            hasResult[i]?.[dayIndex] === true ? (
                                                <FontAwesomeIcon icon={faCheck} size="2xl" style={{ color: "#47d13d" }} />
                                            ) : (
                                                <span
                                                    style={{
                                                        display: "flex",
                                                        alignItems: "center",
                                                        justifyContent: "center",
                                                        padding: "8px",
                                                        backgroundColor: "#f0f0f0", 
                                                        color: "#007bff", 
                                                        border: "1px solid #007bff",
                                                        borderRadius: "5px",
                                                        cursor: "pointer",
                                                        fontWeight: "bold",
                                                        marginTop: "8px", 
                                                        fontSize: "0.9em",
                                                        transition: "background-color 0.3s, color 0.3s",
                                                    }}
                                                    onMouseEnter={(e) => {
                                                        e.currentTarget.style.backgroundColor = "#007bff";
                                                        e.currentTarget.style.color = "#ffffff";
                                                    }}
                                                    onMouseLeave={(e) => {
                                                        e.currentTarget.style.backgroundColor = "#f0f0f0";
                                                        e.currentTarget.style.color = "#007bff";
                                                    }}
                                                    onClick={() => {handleCreateSessionResult(sessionsInfo[i][dayIndex])}}
                                                >
                                                    <FontAwesomeIcon icon={faPlus} style={{ marginRight: "5px" }} /> Añadir resultado
                                                </span>
                                            )
                                        ) : null
                                    ) : (
                                        // Caso en que numWeeks es distinto de 1
                                        trainer.id !== userAuth.id ? (
                                            hasResult[i]?.[dayIndex] === true ? (
                                                <FontAwesomeIcon icon={faCheck} size="xl" style={{ color: "#47d13d" }} />
                                            ) : (
                                                <span
                                                    style={{
                                                        display: "flex",
                                                        alignItems: "center",
                                                        justifyContent: "center",
                                                        padding: "8px",
                                                        backgroundColor: "#f0f0f0", 
                                                        color: "#007bff", 
                                                        border: "1px solid #007bff",
                                                        borderRadius: "5px",
                                                        cursor: "pointer",
                                                        fontWeight: "bold",
                                                        marginTop: "8px", 
                                                        fontSize: "0.9em",
                                                        transition: "background-color 0.3s, color 0.3s",
                                                    }}
                                                    onMouseEnter={(e) => {
                                                        e.currentTarget.style.backgroundColor = "#007bff";
                                                        e.currentTarget.style.color = "#ffffff";
                                                    }}
                                                    onMouseLeave={(e) => {
                                                        e.currentTarget.style.backgroundColor = "#f0f0f0";
                                                        e.currentTarget.style.color = "#007bff";
                                                    }}
                                                    onClick={() => {handleCreateSessionResult(sessionsInfo[i][dayIndex])}}
                                                >
                                                    <FontAwesomeIcon icon={faPlus} style={{ marginRight: "5px" }} /> Añadir resultado
                                                </span>
                                            )
                                        ) : null
                                    )) : null}
                                    




                                </div>
                            ) : (
                                <strong>Descanso</strong>
                            )}
                        </div>
                    ))}
                </div>
            );
        }
        return rows;
    };


    return (
        <>
            <NavigationBar />
            <h1 className="custom-h1">{name}</h1>
            <div className="create-training-plan-container">
                <div className="form-container-create-plan">
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'><FontAwesomeIcon icon={faUser} /> Entrenador:</label>
                        <div className="row">
                            <span>{trainer.name} {trainer.surname}<strong>{' ('}@{trainer.username}{')'}</strong></span>
                            <span>{trainer.experience} años de experiencia</span>
                        </div>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'>Descripción:</label>
                        <span style={{ whiteSpace: 'pre-line' }}>{description}</span>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'>N° de Semanas:</label>
                        <span>{numWeeks}</span>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'><FontAwesomeIcon icon={faMapMarkerAlt} /> Distancia Objetivo:</label>
                        <span>
                            {objDistance}
                        </span>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'><FontAwesomeIcon icon={faSignal} /> Nivel:</label>
                        <span>{level}</span>
                    </div>
                </div>

                <div className="table-container-create-plan">
                    <div className="table-header-create-plan">
                        <div className="day-column-create-plan">Lunes</div>
                        <div className="day-column-create-plan">Martes</div>
                        <div className="day-column-create-plan">Miércoles</div>
                        <div className="day-column-create-plan">Jueves</div>
                        <div className="day-column-create-plan">Viernes</div>
                        <div className="day-column-create-plan">Sábado</div>
                        <div className="day-column-create-plan">Domingo</div>
                    </div>
                    {renderTrainingRows()}
                </div>
            </div>
            <div style={{display: 'flex', justifyContent: 'center'}}>
            {trainer.id === userAuth.id ? (
                <Button variant="primary" size="lg" className="mt-5 custom-button-createact" onClick={handleEdit}>
                    EDITAR
                </Button>
            ) : (
                !isEnrolled ? (
                    <Button variant="primary" size="lg" className="mt-5 custom-button-createact" onClick={handleEnroll}>
                        INSCRIBIRME
                    </Button>
                ) : (
                    <Button variant="danger" size="lg" className="mt-5 custom-button-createact" onClick={handleUnenroll}>
                        DESINSCRIBIRME
                    </Button>
                )
            )}
                    
            </div>
            <ModalSession
                show={showModal}
                handleClose={handleClose}
                session={selectedSession}
            />
            
        </>
    )
}

export default ViewDetails;