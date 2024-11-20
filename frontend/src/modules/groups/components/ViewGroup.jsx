import { useEffect, useState } from "react";
import { useLocation } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import { getGroup } from "../services/groupService";
import { Button, Spinner } from "react-bootstrap";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserGroup, faPersonRunning} from '@fortawesome/free-solid-svg-icons';

function ViewGroup() {
    const location = useLocation()
    const {groupId} = location.state
    const [group,setGroup] = useState(null)
    const [userAuth, setUserAuth] = useState(null)
    useEffect(() => {
        const fetchInfo = async () => {
            const groupInfo = await getGroup(groupId)
            setGroup(groupInfo.data)
            console.log(groupInfo.data)
            setUserAuth(JSON.parse(localStorage.getItem('userAuth')))
        }
        fetchInfo()
    },[])
    if (!group || !userAuth) {
        return (
            <div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                <Spinner animation="border" role="status"/>
            </div>
      )
    }
    return (
        <>
            <NavigationBar />
            <h1 className="custom-h1">Ver grupo:</h1>
            <div className="create-group-container">
                <div className="form-container-create-group">
                    <div className="form-group-create-group">
                        <label className="custom-label-create-group">Entrenador:</label>
                        <span>{group.trainerFullInfo}</span>
                    </div>
                    <div className="form-group-create-group">
                        <label className="custom-label-create-group">Nombre:</label>
                        <span>{group.name}</span>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-group'>Descripción:</label>
                        <span style={{ whiteSpace: 'pre-line' }}>{group.description}</span>
                    </div>
                    <div className="form-group-create-group">
                        <label className="custom-label-create-group"><FontAwesomeIcon icon={faPersonRunning} />Miembros:</label>
                        {group.members?.length > 0 ? (
                            group.members.map((member, index) => (
                                <span key={index}>{member}</span>
                            ))
                            ) : null}
                    </div>
                </div>
            </div>
            {userAuth?.id === group?.trainerId ? (<div style={{ display: "flex", justifyContent: "center" }}>
                <Button variant="primary" size="lg" className="mt-5">
                    EDITAR GRUPO
                </Button>
            </div>) : (null)}
            
        </>
    )
}

export default ViewGroup;