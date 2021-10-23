import { useEffect, useMemo, useState } from 'react';
import PhotoGroup from '../PhotoGroup';
import { withCompanySelector } from '../../hocs/withCompanySelector';
import { withSubmit } from '../../hocs/withSubmit';

const WashingForm = ({ onChange }) => {
    const [emptyTrashImages, setEmptyTrashImages] = useState([]);
    const [fullTrashImages, setFullTrashImages] = useState([]);
    const values = useMemo(() => ({
        emptyTrashImages,
        fullTrashImages,
        isWash: true,
    }), [emptyTrashImages, fullTrashImages]);

    useEffect(() => {
        onChange(values);
    }, [values]);

    return (
        <div>
            <PhotoGroup
                maxPhotosCount={2}
                groupName='До миття'
                onChange={setEmptyTrashImages}
            />
            <PhotoGroup
                maxPhotosCount={2}
                groupName='Після миття'
                onChange={setFullTrashImages}
            />
        </div>
    );
};



export default withSubmit(withCompanySelector(WashingForm));