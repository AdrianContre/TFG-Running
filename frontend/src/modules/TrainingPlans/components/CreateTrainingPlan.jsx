import React, { useState } from "react";
import NavigationBar from "../../home/components/NavigationBar";
import { Modal, Button, Form, Spinner } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/CreateTrainingPlan.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faPenToSquare, faCircleMinus, faCirclePlus} from '@fortawesome/free-solid-svg-icons';
import { createPlan } from "../services/trainingService";
import { useNavigate } from "react-router";
import PopUp from "../../auth/components/PopUp";
import Select from "react-select";
import { useEffect } from "react";
import { getTrainerGroups } from "../../groups/services/groupService";

function CreateTrainingPlan() {
    const navigate = useNavigate()
    const [numWeeks, setNumWeeks] = useState(1);
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [level,setLevel] = useState("Principiante")
    const [objDistance, setObjDistance] = useState("5K")
    const [sessionsInfo, setSessionsInfo] = useState(Array(numWeeks).fill(Array(7).fill(null)));
    const [sessions, setSessions] = useState(Array(numWeeks).fill(Array(7).fill(null)));
    const [showModal, setShowModal] = useState(false);
    const [selectedWeek, setSelectedWeek] = useState(null);
    const [selectedDay, setSelectedDay] = useState(null); 
    const [sessionData, setSessionData] = useState({
        name: '',
        description: '',
        type: '',
        runningType: '',
        distance: '',
        duration: ''
    }); 
    const [show,setShow] = useState(false)
    const [selectedGroups, setSelectedGroups] = useState([]);
    const [groupOptions, setGroupOptions] = useState(null);

    const onHide = (event) => {
        event.preventDefault()
        setShow(false)
    }

    const handleLevelChange = (event) => {
        setLevel(event.target.value); // Actualiza el valor del estado con el seleccionado
    };

    const handleObjChange = (event) => {
        setObjDistance(event.target.value); // Actualiza el valor del estado con el seleccionado
    };

    const updateNumWeeks = (increment) => {
        const newNumWeeks = numWeeks + increment;
        if (newNumWeeks > 0) {
            setNumWeeks(newNumWeeks);
            setSessions(prevSessions => {
                const updatedSessions = [...prevSessions]; 
                if (newNumWeeks > prevSessions.length) {
                    updatedSessions.push(...Array(newNumWeeks - prevSessions.length).fill(Array(7).fill(null)));
                }
                return updatedSessions.slice(0, newNumWeeks);
            });
    
            setSessionsInfo(prevSessionsInfo => {
                const updatedSessionsInfo = [...prevSessionsInfo]; 
                if (newNumWeeks > prevSessionsInfo.length) {
                    updatedSessionsInfo.push(...Array(newNumWeeks - prevSessionsInfo.length).fill(Array(7).fill(null)));
                }
                return updatedSessionsInfo.slice(0, newNumWeeks);
            });
        }
    };

    const handleAddSession = (weekIndex, dayIndex) => {
        setSessionData({
            name: '',
            description: '',
            type: '',
            runningType: '',
            distance: '',
            duration: ''
        });
        setSelectedWeek(weekIndex);
        setSelectedDay(dayIndex);
        setShowModal(true);
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setSessionData((prevState) => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleCreateSession = () => {
        const newSessions = [...sessions];
        newSessions[selectedWeek][selectedDay] = sessionData.name; 
        setSessions(newSessions);
        const newSessionsInfo = [...sessionsInfo];
        newSessionsInfo[selectedWeek][selectedDay] = sessionData;
        setSessionsInfo(newSessionsInfo);
        setShowModal(false); 
        setSessionData({
            name: '',
            description: '',
            type: '',
            runningType: '',
            distance: '',
            duration: ''
        }); 
        console.log(sessionsInfo);
    };

    const handleRemoveSession = (weekIndex, dayIndex) => {
        const newSessions = [...sessions];
        newSessions[weekIndex][dayIndex] = null;
        setSessions(newSessions);
    
        const newSessionsInfo = [...sessionsInfo];
        newSessionsInfo[weekIndex][dayIndex] = null;
        setSessionsInfo(newSessionsInfo);
        console.log(sessionsInfo);
    };

    const handleEditSession = (weekIndex, dayIndex) => {
        const session = sessionsInfo[weekIndex][dayIndex];
        setSessionData(session); 
        setSelectedWeek(weekIndex);
        setSelectedDay(dayIndex);
        setShowModal(true); 
    };

    const handleCreatePlan = async (event) => {
        const trainerId = JSON.parse(localStorage.getItem("userAuth")).id
        event.preventDefault()
        console.log(name)
        console.log(description)
        console.log(numWeeks)
        console.log(level)
        console.log(objDistance)
        console.log(sessionsInfo)
        const allSessionsFilled = sessionsInfo.every(week =>
            week.every(session => session !== null)
        );
        if (allSessionsFilled && name !== "" && description !== "") {
            let groupsId = []
            selectedGroups.map(group => {
                groupsId.push(group.value)
            })
            const send = await createPlan(name, description, numWeeks, objDistance, level, sessionsInfo, trainerId, groupsId)
            if (send) {
                navigate('/myplans')
            }
        }
        else {
            setShow(true)
        }
        
    }

    const renderTrainingRows = () => {
        let rows = [];
        for (let i = 0; i < numWeeks; i++) {
            rows.push(
                <div key={i} className="week-row-create-plan">
                    {[...Array(7)].map((_, dayIndex) => (
                        <div key={dayIndex} className="day-column-create-plan">
                            {sessions[i][dayIndex] ? (
                                <div className="session-cell">
                                    <span className="session-name">{sessions[i][dayIndex]}</span>
                                    <div className="session-icons">
                                        <FontAwesomeIcon
                                            icon={faTrash}
                                            style={{ color: "#f10909", cursor: 'pointer',}}
                                            onClick={() => handleRemoveSession(i, dayIndex)}
                                        />
                                        <FontAwesomeIcon
                                            icon={faPenToSquare}
                                            style={{ cursor: 'pointer', color: '#0000ff' }}
                                            onClick={() => handleEditSession(i, dayIndex)}
                                        />
                                    </div>
                                </div>
                            ) : (
                                <Button
                                    variant="primary"
                                    className="button-create-plan"
                                    onClick={() => handleAddSession(i, dayIndex)}
                                >
                                    Añadir Sesión
                                </Button>
                            )}
                        </div>
                    ))}
                </div>
            );
        }
        return rows;
    };

    useEffect(() => {
        const fetchGroups = async () => {
            const trainerId = JSON.parse(localStorage.getItem('userAuth')).id
            const groups = await getTrainerGroups(trainerId)
            console.log(groups)
            const options = groups.data.map((group) => ({
                value: group.id,
                label: group.name,
            }));
            setGroupOptions(options)
        }
        fetchGroups()
    },[])

    if (!groupOptions) {
        return (
            <div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                <Spinner animation="border" role="status"/>
            </div>
      )
    }

    return (
        <>
            <NavigationBar />
            <h1 className="custom-h1">Crear plan de entrenamiento</h1>
            <div className="create-training-plan-container">
                <div className="form-container-create-plan">
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'>Nombre:</label>
                        <input type="text" className='custom-input-create-plan' value={name} onChange={(e) => setName(e.target.value)} />
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'>Descripción:</label>
                        <textarea className='custom-textarea-create-plan' value={description} onChange={(e) => setDescription(e.target.value)} />
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'>N° de Semanas:</label>
                        <div className="week-counter">
                            <FontAwesomeIcon icon={faCircleMinus} size="xl" style={{color: "#ff0000",cursor: 'pointer', marginRight: '10px',}} onClick={() => updateNumWeeks(-1)}/>
                            <span style={{fontSize: '20px', fontWeight: 'bold'}}>{numWeeks}</span>
                            <FontAwesomeIcon icon={faCirclePlus} size="xl" style={{color: "#0000ff",cursor: 'pointer', marginLeft: '10px',}} onClick={() => updateNumWeeks(1)}/>
                        </div>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'>Distancia Objetivo:</label>
                        <select className='custom-input-create-plan' value={objDistance} onChange={handleObjChange}>
                            <option value="5K">5K</option>
                            <option value="10K">10K</option>
                            <option value="21K">21K</option>
                            <option value="42K">42K</option>
                        </select>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'>Nivel:</label>
                        <select className='custom-input-create-plan' value={level} onChange={handleLevelChange}>
                            <option value="Principiante">Principiante</option>
                            <option value="Intermedio">Intermedio</option>
                            <option value="Avanzado">Avanzado</option>
                        </select>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-plan'>Grupos {'(Opcional)'}:</label>
                        <Select
                            isMulti
                            options={groupOptions}
                            onChange={(selected) => setSelectedGroups(selected)}
                            className="custom-select-createact"
                        />
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
                <Button variant='primary' size='lg' className='mt-5' onClick={handleCreatePlan}>Crear plan de entrenamiento</Button>
            </div>
            <PopUp error={"Todos los campos son obligatorios excepto Grupos. En caso que quieras añadir descanso, añade una sesión de tipo descanso"} show={show} onHide={onHide} title={"Error al crear plan de entrenamiento"}/>

            {/* Modal para agregar sesión */}
            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Crear nueva sesión</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="formSessionName">
                            <Form.Label>Nombre:</Form.Label>
                            <Form.Control
                                type="text"
                                name="name"
                                value={sessionData.name}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="formSessionDescription">
                            <Form.Label>Descripción:</Form.Label>
                            <Form.Control
                                as="textarea"
                                name="description"
                                value={sessionData.description}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="formSessionType">
                            <Form.Label>Tipo:</Form.Label>
                            <Form.Control
                                as="select"
                                name="type"
                                value={sessionData.type}
                                onChange={handleInputChange}
                            >
                                <option value="">Seleccione tipo</option>
                                <option value="running">Carrera</option>
                                <option value="strength">Fuerza</option>
                                <option value="mobility">Movilidad</option>
                                <option value="rest">Descanso</option>
                            </Form.Control>
                        </Form.Group>

                        {sessionData.type === "running" && (
                            <>
                                <Form.Group controlId="formSessionRunningType">
                                    <Form.Label>Tipo de sesión:</Form.Label>
                                        <Form.Control
                                            as="select"
                                            name="runningType"
                                            value={sessionData.runningType}
                                            onChange={handleInputChange}
                                        >
                                            <option value="">Seleccione tipo</option>
                                            <option value="Recovery">Recovery</option>
                                            <option value="Rodaje">Rodaje</option>
                                            <option value="Series">Series</option>
                                            <option value="Fartlek">Fartlek</option>
                                            <option value="Tempo">Tempo</option>
                                        </Form.Control>
                                </Form.Group>
                                <Form.Group controlId="formSessionDistance">
                                    <Form.Label>Distancia: {'(Km)'}</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="distance"
                                        value={sessionData.distance}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>
                                <Form.Group controlId="formSessionDuration">
                                    <Form.Label>Duración: {'(hh:mm:ss)'}</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="duration"
                                        value={sessionData.duration}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>
                            </>
                        )}
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={handleCreateSession}>
                        Crear Sesión
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default CreateTrainingPlan;
