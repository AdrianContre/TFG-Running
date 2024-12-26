import {Route, Routes} from 'react-router-dom'
import Home from '../modules/home/components/Home';
import Login from '../modules/auth/components/Login';
import Register from '../modules/auth/components/Register';
import EditProfile from '../modules/profile/components/editProfile';
import ViewProfile from '../modules/profile/components/viewProfile';
import ListMaterials from '../modules/profile/components/ListMaterials';
import CreateMaterial from '../modules/profile/components/CreateMaterial';
import EditMaterial from '../modules/profile/components/EditMaterial';
import ListActivities from '../modules/activities/components/ListActivities';
import CreateActivity from '../modules/activities/components/CreateActivity';
import ViewManualActivity from '../modules/activities/components/ViewManualActivity';
import EditManualActivity from '../modules/activities/components/EditManualActivity';
import AuthRoute from './AuthRoute';
import ViewTrainingPlans from '../modules/TrainingPlans/components/ViewTrainingPlans';
import CreateTrainingPlan from '../modules/TrainingPlans/components/CreateTrainingPlan';
import ViewMyPlans from '../modules/TrainingPlans/components/ViewMyPlans';
import ViewDetails from '../modules/TrainingPlans/components/ViewDetails';
import ListEnrolledPlans from '../modules/TrainingPlans/components/ListEnrolledPlans';
import EditTrainingPlan from '../modules/TrainingPlans/components/EditTrainingPlan';
import CreateRunningSessionResult from '../modules/TrainingPlans/components/CreateRunningSessionResult';
import CreateSessionResult from '../modules/TrainingPlans/components/CreateSessionResult';
import ViewRunningResult from '../modules/activities/components/ViewRunningResult';
import ViewStrengthResult from '../modules/activities/components/ViewStrenghtResult';
import ViewMobilityResult from '../modules/activities/components/ViewMobilityResult';
import EditRunningResult from '../modules/activities/components/EditRunningResult';
import EditGenericResult from '../modules/activities/components/EditGenericResult';
import TrainingProgress from '../modules/TrainingPlans/components/TrainingProgress';
import ListGroups from '../modules/groups/components/ListGroups';
import CreateGroup from '../modules/groups/components/CreateGroup';
import ListMyGroups from '../modules/groups/components/ListMyGroups';
import ViewGroup from '../modules/groups/components/ViewGroup';
import EditGroup from '../modules/groups/components/EditGroup';
import ListGroupPlans from '../modules/TrainingPlans/components/ListGroupPlans';
import CommentScreen from '../modules/comments/components/CommentScreen';
import TermsConditions from '../modules/terms/components/TermsConditions';
import PrivacyPolitic from '../modules/terms/components/PrivacyPolitic';

function AppRoutes () {
    return (
        <Routes>
            <Route path='/comment' element={<AuthRoute element={CommentScreen} />} />
            <Route path='/groupplans' element={<AuthRoute element={ListGroupPlans} />} />
            <Route path='/editgroup' element={<AuthRoute element={EditGroup} />} />
            <Route path='/viewgroup' element={<AuthRoute element={ViewGroup} />} />
            <Route path='/mygroups' element={<AuthRoute element={ListMyGroups} />} />
            <Route path='/creategroup' element={<AuthRoute element={CreateGroup} />} />
            <Route path='/groups' element={<AuthRoute element={ListGroups} />} />
            <Route path='/trainingprogress' element={<AuthRoute element={TrainingProgress} />} />
            <Route path='/editgenericresult' element={<AuthRoute element={EditGenericResult} />} />
            <Route path='/editrunningresult' element={<AuthRoute element={EditRunningResult} />} />
            <Route path='/viewmobilityresult' element={<AuthRoute element={ViewMobilityResult} />} />
            <Route path='/viewstrengthresult' element={<AuthRoute element={ViewStrengthResult} />} />
            <Route path='/viewrunningresult' element={<AuthRoute element={ViewRunningResult} />} />
            <Route path='/createsessionresult' element={<AuthRoute element={CreateSessionResult} />} />
            <Route path='/createrunningsessionresult' element={<AuthRoute element={CreateRunningSessionResult} />} /> 
            <Route path='/enrolledplans' element={<AuthRoute element={ListEnrolledPlans} />} /> 
            <Route path='/viewplan' element={<AuthRoute element={ViewDetails} />} />
            <Route path='/myplans' element={<AuthRoute element={ViewMyPlans} />} />
            <Route path='/editplan' element={<AuthRoute element={EditTrainingPlan} />} />
            <Route path='/createtrainingplans' element={<AuthRoute element={CreateTrainingPlan} />} />
            <Route path='/trainingplans' element={<AuthRoute element={ViewTrainingPlans} />} />
            <Route path='/editmanualactivity' element={<AuthRoute element={EditManualActivity}/>} />
            <Route path='/viewmanualactivity' element={<AuthRoute element={ViewManualActivity}/>} />
            <Route path='/createactivity' element={<AuthRoute element={CreateActivity}/>} />
            <Route path='/activities' element={<AuthRoute element={ListActivities}/>} />
            <Route path='/editmaterial' element={<AuthRoute element={EditMaterial}/>} />
            <Route path='/creatematerial' element={<AuthRoute element={CreateMaterial}/>} />
            <Route path='/listmaterials' element={<AuthRoute element={ListMaterials}/>} />
            <Route path='/profile' element={<AuthRoute element={ViewProfile}/>} />
            <Route path='/editprofile' element={<AuthRoute element={EditProfile}/>} />
            <Route path='/privacy' element={<PrivacyPolitic/>} />
            <Route path='/terms' element={<TermsConditions/>} />
            <Route path='/login' element={<Login/>} />
            <Route path='/register' element={<Register/>} />
            <Route path='/' element={<Home/>} />
        </Routes>
    )
}

export default AppRoutes;