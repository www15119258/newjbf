/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function listFile (fileQuery) {
  return axios.get(config.system.server_path + '/filemanage/query', {params: fileQuery})
}

export function newFolder (path, name) {
  return axios.post(config.system.server_path + '/filemanage/newFolder', qs.stringify({'path': path, 'name': name}))
}

export function renameFile (path, oldName, newName) {
  return axios.put(config.system.server_path + '/filemanage/renameFile', qs.stringify({'path': path, 'oldName': oldName, 'newName': newName}))
}

export function deleteFile (path, files) {
  return axios.delete(config.system.server_path + '/filemanage/deleteFile', {params: {'path': path, 'files': files}})
}

export function copyFile (copyForm) {
  return axios.post(config.system.server_path + '/filemanage/copyFile', qs.stringify(copyForm))
}

export function uploadFromNet (files) {
  return axios.post(config.system.server_path + '/filemanage/uploadFromNet', files)
}

export let uploadFromLocalServer = config.system.server_path + '/filemanage/uploadFromLocal'
