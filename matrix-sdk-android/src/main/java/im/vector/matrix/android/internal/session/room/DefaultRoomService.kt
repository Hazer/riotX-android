/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.matrix.android.internal.session.room

import androidx.lifecycle.LiveData
import im.vector.matrix.android.api.MatrixCallback
import im.vector.matrix.android.api.session.room.Room
import im.vector.matrix.android.api.session.room.RoomService
import im.vector.matrix.android.api.session.room.RoomSummaryQueryParams
import im.vector.matrix.android.api.session.room.model.RoomSummary
import im.vector.matrix.android.api.session.room.model.create.CreateRoomParams
import im.vector.matrix.android.api.util.Cancelable
import im.vector.matrix.android.api.util.Optional
import im.vector.matrix.android.internal.session.room.alias.GetRoomIdByAliasTask
import im.vector.matrix.android.internal.session.room.create.CreateRoomTask
import im.vector.matrix.android.internal.session.room.membership.joining.JoinRoomTask
import im.vector.matrix.android.internal.session.room.read.MarkAllRoomsReadTask
import im.vector.matrix.android.internal.session.room.summary.RoomSummaryDataSource
import im.vector.matrix.android.internal.session.user.accountdata.UpdateBreadcrumbsTask
import im.vector.matrix.android.internal.task.TaskExecutor
import im.vector.matrix.android.internal.task.configureWith
import javax.inject.Inject

internal class DefaultRoomService @Inject constructor(
        private val createRoomTask: CreateRoomTask,
        private val joinRoomTask: JoinRoomTask,
        private val markAllRoomsReadTask: MarkAllRoomsReadTask,
        private val updateBreadcrumbsTask: UpdateBreadcrumbsTask,
        private val roomIdByAliasTask: GetRoomIdByAliasTask,
        private val roomGetter: RoomGetter,
        private val roomSummaryDataSource: RoomSummaryDataSource,
        private val taskExecutor: TaskExecutor
) : RoomService {

    override fun createRoom(createRoomParams: CreateRoomParams, callback: MatrixCallback<String>): Cancelable {
        return createRoomTask
                .configureWith(createRoomParams) {
                    this.callback = callback
                }
                .executeBy(taskExecutor)
    }

    override fun getRoom(roomId: String): Room? {
        return roomGetter.getRoom(roomId)
    }

    override fun getExistingDirectRoomWithUser(otherUserId: String): Room? {
        return roomGetter.getDirectRoomWith(otherUserId)
    }

    override fun getRoomSummary(roomIdOrAlias: String): RoomSummary? {
        return roomSummaryDataSource.getRoomSummary(roomIdOrAlias)
    }

    override fun getRoomSummaries(queryParams: RoomSummaryQueryParams): List<RoomSummary> {
        return roomSummaryDataSource.getRoomSummaries(queryParams)
    }

    override fun getRoomSummariesLive(queryParams: RoomSummaryQueryParams): LiveData<List<RoomSummary>> {
        return roomSummaryDataSource.getRoomSummariesLive(queryParams)
    }

    override fun getBreadcrumbs(queryParams: RoomSummaryQueryParams): List<RoomSummary> {
        return roomSummaryDataSource.getBreadcrumbs(queryParams)
    }

    override fun getBreadcrumbsLive(queryParams: RoomSummaryQueryParams): LiveData<List<RoomSummary>> {
        return roomSummaryDataSource.getBreadcrumbsLive(queryParams)
    }

    override fun onRoomDisplayed(roomId: String): Cancelable {
        return updateBreadcrumbsTask
                .configureWith(UpdateBreadcrumbsTask.Params(roomId))
                .executeBy(taskExecutor)
    }

    override fun joinRoom(roomIdOrAlias: String, reason: String?, viaServers: List<String>, callback: MatrixCallback<Unit>): Cancelable {
        return joinRoomTask
                .configureWith(JoinRoomTask.Params(roomIdOrAlias, reason, viaServers)) {
                    this.callback = callback
                }
                .executeBy(taskExecutor)
    }

    override fun markAllAsRead(roomIds: List<String>, callback: MatrixCallback<Unit>): Cancelable {
        return markAllRoomsReadTask
                .configureWith(MarkAllRoomsReadTask.Params(roomIds)) {
                    this.callback = callback
                }
                .executeBy(taskExecutor)
    }

    override fun getRoomIdByAlias(roomAlias: String, searchOnServer: Boolean, callback: MatrixCallback<Optional<String>>): Cancelable {
        return roomIdByAliasTask
                .configureWith(GetRoomIdByAliasTask.Params(roomAlias, searchOnServer)) {
                    this.callback = callback
                }
                .executeBy(taskExecutor)
    }
}
