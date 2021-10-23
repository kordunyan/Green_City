import { Card } from 'antd';
import { useState } from 'react';
import GarbageCollectorFormTypeSelect from '../GarbageCollectorFormTypeSelect';
import CollectionForm from '../CollectionForm';
import WashingForm from '../WashingForm';

const GarbageCollectorForm = () => {
    const [formType, setFormType] = useState(null);

    const goBack = () => setFormType(null);

    return (
        <div>
            {!formType && (
                <GarbageCollectorFormTypeSelect onFormTypeSelect={setFormType} />
            )}
            {formType === 'collect' && (
                <CollectionForm goBack={goBack} />
            )}
            {formType === 'wash' && (
                <WashingForm goBack={goBack} />
            )}
        </div>
    )
};

export default GarbageCollectorForm;