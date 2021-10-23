import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmptyTrashImages from './empty-trash-images';
import EmptyTrashImagesDetail from './empty-trash-images-detail';
import EmptyTrashImagesUpdate from './empty-trash-images-update';
import EmptyTrashImagesDeleteDialog from './empty-trash-images-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmptyTrashImagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmptyTrashImagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmptyTrashImagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmptyTrashImages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmptyTrashImagesDeleteDialog} />
  </>
);

export default Routes;
