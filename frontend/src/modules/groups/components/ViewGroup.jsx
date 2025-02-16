import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import { deleteGroup, getGroup } from "../services/groupService";
import { Button, Spinner } from "react-bootstrap";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faPersonRunning} from '@fortawesome/free-solid-svg-icons';
import { useSelector } from "react-redux";

function ViewGroup() {
    const location = useLocation()
    const {groupId} = location.state
    const [group,setGroup] = useState(null)
    const navigate = useNavigate()
    const userAuth = useSelector((state) =>{return state.auth.user})
    useEffect(() => {
        const fetchInfo = async () => {
            const groupInfo = await getGroup(groupId)
            setGroup(groupInfo.data)
        }
        fetchInfo()
    },[])

    const handleEdit = (event) => {
        event.preventDefault()
        navigate('/editgroup',  { state: {groupId: group.id, groupName: group.name, groupDescription: group.description, members: group.members}})
    }

    const handleDelete = async (event) => {
        event.preventDefault()
        const request = await deleteGroup(group.id)
        if (request.data) {
            navigate('/mygroups')
        }
    }

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
                                <span key={index}>{member.name} {member.surname} {'(@'}{member.username}{')'}</span>
                            ))
                            ) : null}
                    </div>
                </div>
            </div>
            {userAuth?.id === group?.trainerId ? (<div style={{ display: "flex", justifyContent: "center" }}>
                <Button variant="primary" size="lg" className="mt-5" onClick={handleEdit}>
                    EDITAR GRUPO
                </Button>
                <Button variant="danger" size="lg" className="mt-5" style={{marginLeft: '100px'}} onClick={handleDelete}>
                    ELIMINAR GRUPO
                </Button>
            </div>) : (null)}
            
        </>
    )
}

export default ViewGroup;